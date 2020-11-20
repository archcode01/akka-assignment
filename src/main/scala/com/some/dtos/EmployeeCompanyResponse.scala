/**
  * Copyright (c) Metro Group
  */
package com.some.dtos

import spray.json.DefaultJsonProtocol
import EmployeeJsonProtocol._

case class EmployeeCompanyResponse (employees:Seq[Employee])

object EmployeeCompanyResponseJsonProtocol extends DefaultJsonProtocol{
  implicit val EmployeeCompanyResponseJsonFormat = jsonFormat1(EmployeeCompanyResponse)
}