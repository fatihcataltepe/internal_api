package com.revolut.internal_api.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.revolut.internal_api.exceptions.{AccountNotFoundException, InsufficientBalanceException}
import com.revolut.internal_api.responses.{MoneyTransactionResponse, MoneyTransactionResponseBuilder}
import org.scalatest.{Matchers, WordSpec}

class MoneyTransactionApiTest extends WordSpec with Matchers with ScalatestRouteTest with MoneyTransactionApi {
  "The service" should {

    "should return 200 valid get request" in {
      Get("/money-transaction?from=2&to=3&amount=50") ~> moneyTransactionApi ~> check {
        val response: MoneyTransactionResponse = responseAs[MoneyTransactionResponse]
        response.status shouldEqual MoneyTransactionResponseBuilder.success.status
        response.message shouldEqual MoneyTransactionResponseBuilder.success.message
        status shouldEqual StatusCodes.OK
      }
    }

    "should return 401 - AccountNotFoundException" in {
      Get("/money-transaction?from=12&to=3&amount=50") ~> moneyTransactionApi ~> check {
        val response: MoneyTransactionResponse = responseAs[MoneyTransactionResponse]
        response.status shouldEqual MoneyTransactionResponseBuilder.failed(AccountNotFoundException(12)).status
        response.message shouldEqual MoneyTransactionResponseBuilder.failed(AccountNotFoundException(12)).message
        status shouldEqual StatusCodes.BadRequest
      }
    }

    "should return 401 - InsufficientBalanceException" in {
      Get("/money-transaction?from=2&to=3&amount=500000") ~> moneyTransactionApi ~> check {
        val response: MoneyTransactionResponse = responseAs[MoneyTransactionResponse]
        response.status shouldEqual MoneyTransactionResponseBuilder.failed(InsufficientBalanceException(15000, 500000)).status
        response.message shouldEqual MoneyTransactionResponseBuilder.failed(InsufficientBalanceException(15000, 500000)).message
        status shouldEqual StatusCodes.BadRequest
      }
    }

  }

}
