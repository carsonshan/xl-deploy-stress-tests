package com.xebialabs.xldeploy.stresstests.runner

import com.typesafe.scalalogging.LazyLogging
import io.gatling.app.Gatling
import io.gatling.core.scenario.Simulation

import scala.util.{Failure, Success, Try}

/**
 * This com.xebialabs.xldeploy.stresstests.runner allows to find and execute simulations from the same classpath as the com.xebialabs.xldeploy.stresstests.runner itself.
 * Simplifies build logic.
 */
object Main extends App with LazyLogging {

  private val simulationPropKey = "simulation"

  logger.info("Starting XL Deploy stress tests suite.")

  private val simulationProvValue = Option(System.getProperty(simulationPropKey)).getOrElse(classOf[CopyFilesSimulation].getCanonicalName)

  private val simulationsToRun = simulationProvValue.split(",").map(simulationClassByName)

  private def simulationClassByName(className: String): Class[_] = {
    Try(Class.forName(className)) match {
      case Success(c) => c
      case Failure(e) =>
        logger.error(s"Can not find simulation $className.")
        throw e
    }
  }

  logger.info(s"Running following simulations: ${simulationsToRun.map(_.getCanonicalName).mkString(", ")}")

  simulationsToRun.foreach {
    case simulation =>
      logger.info(s"Starting simulation $simulation")
      Gatling.fromArgs(args, Some(simulation.asInstanceOf[Class[Simulation]])) match {
        case 0 =>
          logger.info(s"Finished execution of $simulation.")
        case e =>
          logger.info(s"Gatling returned non-zero exit code $e. Exiting.")
          sys.exit(e)
      }
  }

}
