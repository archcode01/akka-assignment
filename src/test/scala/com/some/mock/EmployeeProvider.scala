/**
  * Copyright (c) Metro Group
  */
package com.some.mock

import com.some.dtos.{Employee, EmployeeCompanyResponse}

import scala.collection.immutable
import scala.util.Random

object EmployeeProvider {

  def getMockEmployees(companyId:Int): EmployeeCompanyResponse = {
    EmployeeCompanyResponse((1 to 20).map(x => genEmployee(x)))
  }

  def genEmployee(id:Int) = {
//    val a = Employee(Random.nextString(id), Random.nextString(id), Random.nextFloat(), Random.nextString(id))
    val a  = Employee(s"fname$id", s"lname$id", Random.nextFloat(), s"pos$id")
    println(a)
    a
  }

}
