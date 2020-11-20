/**
  * Copyright (c) Metro Group
  */
package com.some.processors

import com.some.utils.UnitSpec
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._

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


  "EmployeeReader" should "read employees for a company" in {
    val path = "/my/resource"
    stubFor(get(urlEqualTo(path))
      .willReturn(
        aResponse()
          .withStatus(200)))
    val request = url(s"http://$Host:$Port$path").GET
    val responseFuture = Http(request)

    val response = Await.result(responseFuture, Duration(100, TimeUnit.MILLISECONDS))
    response.getStatusCode should be(200)
  }



}
