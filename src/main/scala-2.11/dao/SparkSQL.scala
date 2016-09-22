package dao

import org.apache.spark.sql.SparkSession
import scaldi.{Injectable, Injector}
import util.{Calculator, Parser}

/**
  * Created by pbezglasnyi on 21.09.2016.
  */
class SparkSQL(implicit inj: Injector) extends DAO with Injectable {

  System.setProperty("hadoop.home.dir", "C:\\winutil\\")

  val labelsLocation = inject[String](identified by "labels.location")
  val gridsLocation = inject[String](identified by "grids.location")

  val spark = SparkSession.builder().appName("name").master("local").
    config("spark.sql.warehouse.dir", "file:///C:/work/spark_dir/").getOrCreate()


  spark.udf.register("haversine", Calculator.haversine(_: Double, _: Double, _: Double, _: Double))

  val labels = spark.read.option("header", true).csv(labelsLocation)
  val grids = spark.read.option("header", true).csv(gridsLocation)

  override def isNear(lat: Double, lon: Double, user_id: Int): Boolean = {
    val label = labels.selectExpr("lon", "lat", s"haversine(lat, lon, $lat, $lon)").where(s"user_id = $user_id").take(1) match {
      case array if array.length == 1 => Some(array(0))
      case _ => None
    }
    val labelSelect = label.map(row => (row(0), row(1), row(2))).map(t => new LabelSelect(t._1, t._2, t._3))
    val grid = labelSelect.flatMap(l => grids.selectExpr(s"distance_error > ${l.distance}").where(s"tile_x = ${l.lat} and tile_y= ${l.lon}").take(1) match {
      case array if array.length == 1 => Some(array(0))
      case _ => None
    }).map(_ (0))
    grid.exists(b => b.toString.toBoolean)
  }

  private case class LabelSelect(lon: Int, lat: Int, distance: Double) {
    def this(lon: Any, lat: Any, distance: Any) {
      this(Parser.parseIntDouble(lon).toInt, Parser.parseIntDouble(lat).toInt, Parser.parseIntDouble(distance).toInt)
    }
  }

  override def countLabelsInInGrid(lon: Int, lat: Int): Long = {
    labels.where(s"lon > $lon AND lon < $lon + 1 AND lat > $lat AND lat < $lat + 1").count()
  }
}
