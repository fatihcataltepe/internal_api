package com.revolut.internal_api

import com.typesafe.scalalogging.LazyLogging
import services.WebApiService

object Main extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.info("Revolut internal-api webservice is starting...")

    val webApiService: WebApiService = new WebApiService()
    webApiService.start()
  }
}
