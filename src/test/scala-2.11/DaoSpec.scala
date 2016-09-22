import dao.{DAO, SparkSQL}
import module.TestModule
import org.scalatest.FlatSpec
import scaldi.{Injectable, Injector}

/**
  * Created by pbezglasnyi on 22.09.2016.
  */
class DaoSpec extends FlatSpec with Injectable {

  implicit val injector=new TestModule


  "Labels" should "work right" in {
    val dao: DAO = new SparkSQL
    dao.isNear(3.1, 4.3, 10)
  }

}
