package dao

import org.apache.spark.sql.SparkSession
import scaldi.{Injectable, Injector}
import util.Calculator

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
    val arr = labels.selectExpr("lon", "lat", s"haversine(lat, lon, $lat, $lon)").where(s"user_id = $user_id").take(1)
    val label = arr match {
      case array if array.length == 1 => Some(arr(0))
      case _ => None
    }
    val vargs = label.map(row => (row(0), row(1), row(2))).map(t => (t._1.toString.toDouble.toInt, t._2.toString.toDouble.toInt, t._2.toString.toDouble.toInt))
    val grid = Option(grids.select("distance_error").where("tile_x = 31 and tile_y=41"))
    true
  }


}
