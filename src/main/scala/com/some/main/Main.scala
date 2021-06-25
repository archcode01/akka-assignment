/**
  * Copyright (c) Metro Group
  */
package com.some.main

import com.some.processors.{CompanyInfoProvider, EmployeeReader}

object Main extends App {

  EmployeeReader.readEmployees(CompanyInfoProvider.companyIds)

}
