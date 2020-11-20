/**
  * Copyright (c) Metro Group
  */
package com.some.mock

import com.some.dtos.Employee

import scala.collection.immutable
import scala.util.Random

object EmployeeProvider {

  def getMockEmployees(companyId:Int): immutable.Seq[Employee] = {
    (1 to 20).map(x => genEmployee(x))
  }

  def genEmployee(id:Int) = {
    Employee(Random.nextString(id), Random.nextString(id), Random.nextFloat(), Random.nextString(id))
  }

}
