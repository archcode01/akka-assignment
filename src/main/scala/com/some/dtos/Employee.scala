/**
  * Copyright (c) Metro Group
  */
package com.some.dtos

import spray.json.DefaultJsonProtocol

case class Employee (firstName:String, lastName:String, salary:Float, age:Int)

object EmployeeJsonProtocol extends DefaultJsonProtocol{
  implicit val EmployeeJsonFormat = jsonFormat4(Employee)
}
