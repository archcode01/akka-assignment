/**
  * Copyright (c) Metro Group
  */
package com.some.utils

import akka.actor.ActorSystem
import akka.stream.Materializer

object ActorEssentials {

  implicit val actorSystem = ActorSystem("employeeReader")
  implicit val materializer = Materializer(actorSystem)
  implicit val executionContext = actorSystem.dispatcher

}
