package org.opencoin.issuer

import org.scalaquery._
import ql._
import basic.{ BasicTable => Table, _ }
import basic.BasicDriver.Implicit._
import session._
import java.util.Date
import java.net.URL
import org.opencoin.core.util.Base64
import org.opencoin.core.token.CDD
import org.opencoin.core.token.Coin
import org.opencoin.core.token.MintKeyCertificate
import org.opencoin.issuer.TypeMappers._
import org.scalaquery.session.Database.threadLocalSession

object DSDBTable extends Table[DSDB]("DSDB") {
  
  def serial = column[Base64]("serial", O NotNull) //TODO unique
  def signature = column[Base64]("signature", O NotNull)
  def mintKeyID = column[Base64]("mintKeyID", O NotNull)
  def date = column[Date]("date", O NotNull)
  
  def * = serial ~ signature ~ mintKeyID ~ date <> (DSDB, DSDB.unapply _)
  
  //TODO Is verification against serial enough/good? Or should signature be considered too?
  def notSpent(db: Database, coin: Coin): Boolean = {
    db withSession { //s: Session =>
      val res = for ( b <- DSDBTable if b.serial === coin.token.serial) yield b
	  if(res == Nil) true
	  else false
    }
  }
  
  def storeInDSDB(db: Database, coins: List[Coin]): Boolean = {
    val date = new Date
    db withSession { //s: Session => 
	  coins.foreach(c => DSDBTable.insert(DSDB(c.token.serial, c.signature, c.token.mint_key_id, date)))
	  //Maybe "insertAll" is faster than "insert", but the following doesn't work. Is it a bug in Scalaquery 0.9.5?
	  //DSDBTable.insertAll(coins.map(c => DSDB(c.token.serial, c.signature, c.token.mint_key_id, date)))
	}
	if(db==coins.length) true
	else false
  }

}