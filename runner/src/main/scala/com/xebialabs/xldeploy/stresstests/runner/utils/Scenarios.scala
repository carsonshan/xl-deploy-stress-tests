package com.xebialabs.xldeploy.stresstests.runner.utils

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.StringBody
import com.xebialabs.xldeploy.stresstests.runner.chain._
import com.xebialabs.xldeploy.stresstests.runner.config.RunnerConfig._

import scala.language.{implicitConversions, postfixOps}

object Scenarios {

  val readRepositoryScenario = scenario("Read repository").exec(Repository.read("Environments/dir1/dict1"));

}