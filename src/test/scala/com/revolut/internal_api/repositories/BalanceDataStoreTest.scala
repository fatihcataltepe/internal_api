package com.revolut.internal_api.repositories

import com.revolut.internal_api.exceptions.AccountNotFoundException
import org.scalatest.FunSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BalanceDataStoreTest extends FunSpec {

  describe("BalanceDataStoreTest") {

    it("testExecuteTransaction success") {
      val db: BalanceDataStore = BalanceDataStore()

      db.executeTransaction(5, 2, 1000).onComplete { _ =>
        assert(db.getBalanceOfUser(5).get == 126000)
        assert(db.getBalanceOfUser(2).get == 16000)
      }
    }

    it("testExecuteTransaction from user not found") {
      val db: BalanceDataStore = BalanceDataStore()
      val transaction: Future[Unit] = db.executeTransaction(15, 2, 1000)

      transaction.onSuccess{ case _ => assert(false)}
      transaction.onFailure {
        case x: AccountNotFoundException => assert(x.userId == 15)
      }
    }

    it("testExecuteTransaction to user not found") {
      val db: BalanceDataStore = BalanceDataStore()
      val transaction: Future[Unit] = db.executeTransaction(5, 12, 1000)

      transaction.onSuccess{ case _ => assert(false)}
      transaction.onFailure {
        case x: AccountNotFoundException => assert(x.userId == 12)
      }
    }

  }
}
