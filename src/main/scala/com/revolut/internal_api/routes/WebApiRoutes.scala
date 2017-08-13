package com.revolut.internal_api.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * Trait for defining routes to be used for web api.
  */
trait WebApiRoutes extends WebApiExceptionHandler with MoneyTransactionApi {

  /**
    * Defined routes for application.
    */
  val routes: Route =
    pathPrefix("api") {
      pathPrefix("v1.0") {
        moneyTransactionApi
      }
    }

}
