package com.revolut.internal_api.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.revolut.internal_api.exceptions.{AccountNotFoundException, InsufficientBalanceException}
import com.revolut.internal_api.repositories.BalanceDataStore
import com.revolut.internal_api.responses.MoneyTransactionResponseBuilder
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Trait for defining routes to be used for `money-transaction`.
  */
trait MoneyTransactionApi extends JsonSupport with LazyLogging {

  /**
    * Defined routes for `money-transaction`.
    */
  val moneyTransactionApi: Route =
    get {
      path("money-transaction") {
        parameters('from.as[Int], 'to.as[Int], 'amount.as[Double]) {
          (from, to, amount) =>

            val transaction: Future[Unit] = BalanceDataStore.executeTransaction(from, to, amount)

            onComplete(transaction) {
              case Success(_) =>
                logger.debug(BalanceDataStore.currState) //print the current state of the data store
                complete(MoneyTransactionResponseBuilder.success)

              case Failure(e: InsufficientBalanceException) => complete(StatusCodes.BadRequest, MoneyTransactionResponseBuilder.failed(e))

              case Failure(e: AccountNotFoundException) => complete(StatusCodes.BadRequest, MoneyTransactionResponseBuilder.failed(e))

              case Failure(e) => complete(StatusCodes.InternalServerError, MoneyTransactionResponseBuilder.failed(e))
            }

        }
      }
    }

}
