package module

import dao.{DAO, HiveDao}
import scaldi.Module

/**
  * Created by pbezglasnyi on 22.09.2016.
  */
class TestModule extends Module {

  binding identifiedBy "labels.location" to "src/test/resources/labels.csv"
  binding identifiedBy "grids.location" to "src/test/resources/grids.csv"
  bind[DAO] to new HiveDao

}
