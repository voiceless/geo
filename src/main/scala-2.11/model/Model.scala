package model

/**
  * Created by pbezglasnyi on 20.09.2016.
  */
case class Label(user_id: Int, lon: Double, lat: Double) {
  def toCsvString = s"$user_id,$lon,$lat"
}

case class Grid(tile_x: Int, tile_y: Int, distance_error: Double) {
  def toCsvString = s"$tile_x,$tile_y,$distance_error"
}
