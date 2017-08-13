package com.revolut.internal_api.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.revolut.internal_api.contexts.WebContext
import com.revolut.internal_api.routes.WebApiRoutes

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Service for web api.
  */
class WebApiService extends WebApiRoutes {

  /**
    * Lazy load current web context.
    */
  lazy val context: WebContext = new WebContext()

  /**
    * Starts web api service.
    */
  def start(): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher
    val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(routes, context.getApiUrl, context.getApiPort)
    println(s"Web api service online at http://${context.getApiUrl}:${context.getApiPort}/\nPress RETURN to stop...")
    scala.io.StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }

}