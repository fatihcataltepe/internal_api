package com.revolut.internal_api.services

import com.revolut.internal_api.responses.MoneyTransactionResponse

class MoneyTransactionService {

  def executeTransaction(from: Int, to: Int, amount: Int): MoneyTransactionResponse = {
    MoneyTransactionResponse("Dummy", "Response")
  }

}
