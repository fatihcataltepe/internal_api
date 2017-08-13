package com.revolut.internal_api.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.revolut.internal_api.responses.MoneyTransactionResponse
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * Trait for JSON support.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  /**
    * Implicitly declare value for instance of [[MoneyTransactionResponse]] class with default JSON protocol.
    */
  implicit val moneyTransactionResponse: RootJsonFormat[MoneyTransactionResponse] = jsonFormat2(MoneyTransactionResponse)

}
