import dao.{DAO, SparkSQL}
import module.TestModule
import org.scalatest.FlatSpec
import scaldi.Injectable

/**
  * Created by pbezglasnyi on 22.09.2016.
  */
class DaoSpec extends FlatSpec with Injectable {

  implicit val injector = new TestModule


  "Labels" should "work right" in {
    val dao: DAO = new SparkSQL
    val b = dao.isNear(3.1, 4.3, 10)
    val i = 1
  }

}
