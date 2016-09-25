package dao

import org.apache.spark.sql.SparkSession
import scaldi.{Injectable, Injector}
import util.{Calculator, Parser}

/**
  * Created by pbezglasnyi.
  */
class HiveDao(implicit inj: Injector) extends DAO with Injectable {

  val labelsLocation = inject[String](identified by "labels.location")
  val gridsLocation = inject[String](identified by "grids.location")

  val spark = SparkSession.builder().appName("name").master("local").
    config("spark.sql.warehouse.dir", "file:///D:/work/spark_dir/").enableHiveSupport().getOrCreate()

  import spark.sql

  sql("DROP TABLE IF EXISTS label")
  sql("CREATE TABLE IF NOT EXISTS label (user_id INT, lon DOUBLE, lat DOUBLE) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'")
  sql(s"LOAD DATA LOCAL INPATH '$labelsLocation' INTO TABLE label")

  sql("DROP TABLE IF EXISTS grid")
  sql("CREATE TABLE IF NOT EXISTS grid (tile_x INT,tile_y INT,distance_error DOUBLE) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'")
  sql(s"LOAD DATA LOCAL INPATH '$gridsLocation' INTO TABLE grid")



  spark.udf.register("haversine", Calculator.haversine(_: Double, _: Double, _: Double, _: Double))

  override def isNear(lat: Double, lon: Double, user_id: Int): Boolean = {
    val label = sql(s"SELECT lon, lat, haversine(lat, lon, $lat, $lon) FROM label WHERE user_id = $user_id").take(1) match {
      case array if array.length == 1 => Some(array(0))
      case _ => None
    }
    val labelSelect = label.map(row => (row(0), row(1), row(2))).map(t => new LabelSelect(t._1, t._2, t._3))
    val grid = labelSelect.flatMap(l => sql(s"SELECT distance_error > ${l.distance} FROM grid WHERE tile_x = ${l.lat} and tile_y= ${l.lon}").take(1) match {
      case array if array.length == 1 => Some(array(0))
      case _ => None
    }).map(_ (0))
    grid.exists(b => b.toString.toBoolean)
  }

  override def countLabelsInInGrid(lon: Int, lat: Int): Long = {
    val s = sql(s"SELECT COUNT(*) FROM label WHERE lon > $lon AND lon < $lon + 1 AND lat > $lat AND lat < $lat + 1").take(1) match {
      case array if array.length == 1 => array(0)(0).toString.toLong
      case _ => 0
    }
    s
  }

  private case class LabelSelect(lon: Int, lat: Int, distance: Double) {
    def this(lon: Any, lat: Any, distance: Any) {
      this(Parser.parseDouble(lon).toInt, Parser.parseDouble(lat).toInt, Parser.parseDouble(distance).toInt)
    }
  }
}
