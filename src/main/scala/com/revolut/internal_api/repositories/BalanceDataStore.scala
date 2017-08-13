package com.revolut.internal_api.repositories

import com.revolut.internal_api.exceptions._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Data store that keeps user's balance information
  * For now, it is a fake data store
  *
  * In the future, this can be replaced by a real database
  */
object BalanceDataStore {
  /**
    * Fake map that stores user and their balances
    * Users are represented by int (consider as user id)
    * Balances are represented by Double (consider balance in terms of a currency)
    *
    * I should have used a val type here, however since it is a fake datastore, I used var to update it's state
    */
  private var balances: Map[Int, Double] = Map(1 -> 10000, 2 -> 15000, 3 -> 7500, 4 -> 140.2, 5 -> 127000, 6 -> 12025)


  /**
    * The method to update data store
    * In this version, there is no need for a future implementation
    * However, In a real scenerio while using a real database, this function has to be implemented in a different thread
    * That's why I encapsulated method into a Future
    *
    * @param from   the user id of money sender
    * @param to     the user id of money receiver
    * @param amount the amount of money to be transferred
    * @return If successful nothing, otherwise throws exception
    */
  def executeTransaction(from: Int, to: Int, amount: Double): Future[Unit] = Future {
    (balances.get(from), balances.get(to)) match {
      case (None, _) => throw AccountNotFoundException(from)
      case (_, None) => throw AccountNotFoundException(to)
      case (Some(fromBalance), _) if fromBalance < amount => throw InsufficientBalanceException(fromBalance, amount)
      case (Some(fromBalance), Some(toBalance)) =>
        balances = balances.updated(from, fromBalance - amount).updated(to, toBalance + amount) //update users' accounts
    }
  }

  /**
    * Returns the account of the given user
    * @param user user id
    * @return the balance of user if exist
    */
  def getBalanceOfUser(user: Int): Option[Double] = balances.get(user)


  /**
    * For debug purposes
    *
    * @return the formatted string of balances
    */
  override def toString: String = balances.toList.sortBy(_._1).map(x =>s"${x._1},${x._2}").mkString("\n")

}