package util

import org.apache.hadoop.hive.ql.exec.UDF

/**
  * Created by pbezglasnyi.
  */
class HaversineUDF extends UDF {

  def evaluate(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double = {
    Calculator.haversine(lat1, lon1, lat2, lon2)
  }

}
