package dao

import model.Label

/**
  * Created by pbezglasnyi on 21.09.2016.
  */
trait DAO {

  def isNear(lat: Double, lon: Double, user_id: Int): Boolean

  def countLabelsInInGrid(lon: Int, lat: Int): Long


}
