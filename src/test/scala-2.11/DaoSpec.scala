import dao.DAO
import model.Label
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

  "Labels" should "work correctly" in {
    dao.isNear(1, 1, 10) should be(true)
    dao.isNear(3, 3, 10) should be(false)
  }

  "Count" should "work correctly" in {
    dao.countLabelsInInGrid(1, 1) should be(3)
    dao.countLabelsInInGrid(2, 2) should be(1)
    dao.countLabelsInInGrid(41, 15251) should be(0)
  }

  "Insert user" should "work correctly" in {
    dao.insertUser(1, 1)
    dao.getUser(11) should be(Some(Label(11, 1, 1)))
  }

  "Update user" should "work correcly" in {
    dao.updateUser(11, 2, 1)
    dao.getUser(11) should be(Some(Label(11, 2, 1)))
  }

  "Delete user" should "work correctly" in {
    dao.insertUser(1, 1)
    dao.deleteUser(11)
    dao.getUser(11) should be(None)
  }

}
