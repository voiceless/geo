import dao.{DAO, SparkSQL}
import module.TestModule
import org.scalatest.{FlatSpec, Matchers}
import scaldi.Injectable

/**
  * Created by pbezglasnyi on 22.09.2016.
  */
class DaoSpec extends FlatSpec with Injectable with Matchers {

  implicit val injector = new TestModule
  val dao: DAO = new SparkSQL

  "Labels" should "work right" in {
    val b = dao.isNear(3.1, 4.3, 10)
    val i = 1
  }

  "Count" should "work correctly" in {
    dao.countLabelsInInGrid(1, 1) should be (2)
    dao.countLabelsInInGrid(2, 2) should be (1)
    dao.countLabelsInInGrid(41, 15251) should be (0)
  }

}
