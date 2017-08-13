package com.revolut.internal_api.exceptions

/**
  * Insufficient balance exception.
  *
  */
case class InsufficientBalanceException() extends Exception("User does not have sufficient balance to complete transaction request")