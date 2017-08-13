package com.revolut.internal_api.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.revolut.internal_api.exceptions.{AccountNotFoundException, InsufficientBalanceException}
import com.revolut.internal_api.repositories.BalanceDataStore
import com.revolut.internal_api.responses.MoneyTransactionResponse

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Trait for defining routes to be used for `money-transaction`.
  */
trait MoneyTransactionApi extends JsonSupport {
  /**
    * Defined routes for `money-transaction`.
    */
  val moneyTransactionApi: Route =
    get {
      path("money-transaction") {
        parameters('from.as[Int], 'to.as[Int], 'amount.as[Int]) {
          (from, to, amount) =>
            val dataStore: BalanceDataStore = BalanceDataStore()
            val transaction: Future[Unit] = dataStore.executeTransaction(from, to, amount)

            onComplete(transaction) {
              case Success(_) => complete(MoneyTransactionResponse("OK", "Success"))
              case Failure(ex) => ex match {
                case e: InsufficientBalanceException => complete(HttpResponse(StatusCodes.BadRequest, entity = e.getMessage))
                case e: AccountNotFoundException => complete(HttpResponse(StatusCodes.BadRequest, entity = e.getMessage))
                case _: Throwable => complete(HttpResponse(StatusCodes.InternalServerError))
              }
            }

        }
      }
    }

}
