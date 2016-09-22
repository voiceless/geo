package dao

/**
  * Created by pbezglasnyi on 21.09.2016.
  */
trait DAO {

  def isNear(lat: Double, lon: Double, user_id:Int): Boolean

}
