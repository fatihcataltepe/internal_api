package com.revolut.internal_api.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.ExceptionHandler
import com.revolut.internal_api.exceptions.InsufficientBalanceException

/**
  * Trait for exception handling in web api.
  */
trait WebApiExceptionHandler {

  /**
    * Exception handler for `money-transaction`.
    *
    * @return Exception handler
    */
  implicit def moneyTransactionExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: InsufficientBalanceException => complete(HttpResponse(StatusCodes.BadRequest, entity = e.getMessage))
    case _: Throwable => complete(HttpResponse(StatusCodes.InternalServerError))
  }

}