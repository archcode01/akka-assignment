# Assignment on Akka Streams and Scala
Create a project from scratch with below requirements
* Create a branch from this main repository with your {firstName}_{lastName}
* Create a sbt project with scala version 2.12.10 + any open jdk version(8 or 11)
* You are writing a service that fetches a list of employees from a third party application. Assume a simple third party API which gives you the list of employees for one, some or all companies. Structure:: Company(1..n)>>Employee(1..n)>>Employee_fields.
* Create and start a mock server which simulates the third party system and serves REST calls to delivery employees from 100 different companies. Each company will have 100-200 employees. Only 3-4 fields needed for each employee.  Structure:: Company(1..n)>>Employee(1..n)>>Employee_fields.
* Make sure the mock is embedded in tests not the standalone. You can use [Wiremock](http://wiremock.org/docs)
* Write the code to stream using akka the http REST calls which would be served using the mock server in earlier steps.
* The streams must be resilient which means if the mock server is down for 10-20 seconds it should get the data from the mock server once it is up.
* Use non blocking calls (Futures).
* Write the test cases for +ve and -ve scenarios where -ve would be the rest endpoint goes down for 10 seconds but stream recovers and is able to fetch the data as soon as the endpoint is up again.


#Solution
Akka Stream implementation