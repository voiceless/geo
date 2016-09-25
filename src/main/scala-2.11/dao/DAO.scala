package dao

import model.Label

/**
  * Created by pbezglasnyi on 21.09.2016.
  */
trait DAO {

  def isNear(lat: Double, lon: Double, user_id: Int): Boolean

  def countLabelsInInGrid(lon: Int, lat: Int): Long

  def insertUser(lon: Double, lat: Double): Unit

  def updateUser(user_id: Int, lon: Double, lat: Double): Unit

  def deleteUser(user_id: Int): Unit

  def getUser(user_id: Int):Label

}
