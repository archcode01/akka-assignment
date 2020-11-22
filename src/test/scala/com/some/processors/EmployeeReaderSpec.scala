/**
  * Copyright (c) Metro Group
  */
package com.some.processors

import com.some.utils.UnitSpec
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.{ResponseDefinitionBuilder, WireMock}
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder._
import com.github.tomakehurst.wiremock.common.Json
import com.some.dtos.{Employee, EmployeeCompanyResponseJsonProtocol}
import com.some.mock.EmployeeProvider

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

class EmployeeReaderSpec extends UnitSpec{

  val Port = 8080
  val Host = "localhost"
  val wireMockServer = new WireMockServer(wireMockConfig().port(Port))

  override def beforeEach {
    wireMockServer.start()
    WireMock.configureFor(Host, Port)
  }

  override def afterEach {
    wireMockServer.stop()
  }


  var testAccumulator = new collection.mutable.Queue[Seq[Employee]]

  def accumulator(emps:Seq[Employee]):Unit = testAccumulator.enqueue(emps)
  def clearAccumulator = testAccumulator.clear()

  "EmployeeReader" should "read employees for a company" in {
    stub
    clearAccumulator
    val resultFut = EmployeeReader.readEmployees(Seq(1),accumulator)
    val result = Await.result(resultFut, 5 seconds)
    assert(testAccumulator.size == 1)
  }


//negative tests
  it should "read employees for a company even if the call fails and retry should work" in {
    errorstub
    clearAccumulator
    val resultFut = EmployeeReader.readEmployees(Seq(1),accumulator)
    //make the api available
    val a: Future[Unit] = Future.successful(stub).map(_ => Thread.sleep(10000))
    Await.result(a, 25 seconds)
    val result = Await.result(resultFut, 50 seconds)
    assert(testAccumulator.size == 1)
  }

  def stub = {
    val path = "/companies/1/employees"

    stubFor(get(urlEqualTo(path))
      .willReturn(
        aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(genJson)
      )
    )
  }

  def genJson = {
    val emps = EmployeeProvider.getMockEmployees(Random.nextInt())
    EmployeeCompanyResponseJsonProtocol.EmployeeCompanyResponseJsonFormat.write(emps).toString
  }


  def errorstub = {
    val path = "/companies/1/employees"

    stubFor(get(urlEqualTo(path))
      .willReturn(
        aResponse()
          .withStatus(503)
      )
    )
  }
}
