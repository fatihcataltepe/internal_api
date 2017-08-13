package com.revolut.internal_api.exceptions

/**
  * Exceptions that can occur using web-api service.
  */
case class InsufficientBalanceException(currAmount: Double, transactionAmount: Double) extends Exception(s"Current account ($currAmount) is not sufficient to transfer $transactionAmount")

case class AccountNotFoundException(userId: Int) extends Exception(s"Account related to user: $userId does not exist")