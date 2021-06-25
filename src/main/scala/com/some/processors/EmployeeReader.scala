/**
  * Copyright (c) Metro Group
  */
package com.some.processors


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, ResponseEntity, StatusCodes}
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.{ActorMaterializer, RestartSettings}
import akka.stream.scaladsl.{RestartSource, Sink, Source}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.some.dtos.EmployeeCompanyResponseJsonProtocol._
import com.some.dtos.{Employee, EmployeeCompanyResponse}

import scala.concurrent.Future
import scala.concurrent.duration._
import com.some.utils.ActorEssentials._
import com.some.exceptions.{CustomException, StreamFailedException}

import scala.collection.immutable
import scala.util.{Failure, Success}

object EmployeeReader {

  val employeeUrl = s"http://localhost:8080/companies/companyId/employees"

  val settings = RestartSettings(
    minBackoff = 3.seconds,
    maxBackoff = 30.seconds,
    randomFactor = 0.2 // adds 20% "noise" to vary the intervals slightly
  ).withMaxRestarts(20, 5.minutes) // limits the amount of restarts to 20 within 5 minutes



  def getResponse(id: Int)(implicit um:Unmarshaller[ResponseEntity, EmployeeCompanyResponse]) : Future[EmployeeCompanyResponse] = {
    RestartSource.withBackoff(settings) { () =>
      val responseFuture: Future[HttpResponse] =
        Http().singleRequest(HttpRequest(uri = employeeUrl.replace("companyId", id.toString)))

      Source.future(responseFuture)
        .mapAsync(parallelism = 1) {
          case HttpResponse(StatusCodes.OK, _, entity, _) =>
            println(entity.toStrict(2 seconds).toString)
            Unmarshal(entity).to[EmployeeCompanyResponse](um,actorSystem.dispatcher,materializer)
          case HttpResponse(StatusCodes.InternalServerError, _, _, _) =>
            throw new CustomException
          case HttpResponse(statusCode, _, _, _) =>
            throw new CustomException
        }
    }
      .runWith(Sink.head)
      .recover {
        case _ => throw new StreamFailedException
      }(actorSystem.dispatcher)
  }

  private def companies = Source(1 to 100)



  def readEmployees(companyIds:Seq[Int], accumulator:Seq[Employee] => Unit = Accumulator.printEmployees): Future[Unit] = {
    val aggregate = Source.fromIterator(() => companyIds.toIterator)
      .mapAsync(parallelism = 2)(getResponse)
      .map(_.employees)
      .runWith(Sink.foreach(accumulator))

    aggregate.map(_  => println(s"Stream completed Successfully ")).recover{
      case ex:Throwable => sys.error(s"Stream something wrong : $ex");throw ex
    }
  }

}
