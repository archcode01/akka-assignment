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
import com.some.dtos.Employee
import com.some.mock.EmployeeProvider

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

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

  "EmployeeReader" should "read employees for a company" in {

    stub
    val resultFut = EmployeeReader.readEmployees(Seq(1),accumulator)

    val result = Await.result(resultFut, 5 seconds)
    assert(testAccumulator.size == 1)
  }

  def stub = {
    val path = "/companies"

    stubFor(get(urlMatching(path))
      .willReturn(
        aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(Json.write(EmployeeProvider.getMockEmployees(Random.nextInt())))
      )
    )
  }


}
