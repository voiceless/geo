import dao.DAO
import module.TestModule
import org.scalatest.{FlatSpec, Matchers}
import scaldi.Injectable

/**
  * Created by pbezglasnyi on 22.09.2016.
  */
class DaoSpec extends FlatSpec with Injectable with Matchers {

  System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.6.0")

  implicit val injector = new TestModule
  val dao: DAO = inject[DAO]

  "Labels" should "work right" in {
    dao.isNear(1, 1, 10) should be(true)
    dao.isNear(3, 3, 10) should be(false)
  }

  "Count" should "work correctly" in {
    dao.countLabelsInInGrid(1, 1) should be(2)
    dao.countLabelsInInGrid(2, 2) should be(1)
    dao.countLabelsInInGrid(41, 15251) should be(0)
  }

}
