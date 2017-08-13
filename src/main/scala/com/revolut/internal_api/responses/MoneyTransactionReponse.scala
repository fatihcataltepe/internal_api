package com.revolut.internal_api.responses

case class MoneyTransactionResponse(status: String, message: String)

object MoneyTransactionResponse {

  def success: MoneyTransactionResponse = MoneyTransactionResponse("Success", "Transaction is completed")

  def failed(e: Throwable): MoneyTransactionResponse = MoneyTransactionResponse("Error", e.getMessage)
}
