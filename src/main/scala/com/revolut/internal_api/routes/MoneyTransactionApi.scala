package com.revolut.internal_api.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.revolut.internal_api.responses.MoneyTransactionResponse
import com.revolut.internal_api.services.MoneyTransactionService

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
          (from,to,amount) =>
            val moneyTransactionService: MoneyTransactionService = new MoneyTransactionService()
            val result: MoneyTransactionResponse = moneyTransactionService.executeTransaction(from, to, amount)
            complete(result)
        }
      }
    }

}
