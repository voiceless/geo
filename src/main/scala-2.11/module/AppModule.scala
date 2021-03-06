package module

import akka.actor.ActorSystem
import dao.{DAO, HiveDao}
import scaldi.Module
import service.GeoServiceActor

/**
  * Created by pbezglasnyi.
  */
class AppModule(labelLocation: String, gridLocation: String) extends Module {

  binding identifiedBy "labels.location" to labelLocation
  binding identifiedBy "grids.location" to gridLocation
  bind[DAO] to new HiveDao
  bind[ActorSystem] to ActorSystem("ScaldiExample") destroyWith (_.terminate())
  binding toProvider new GeoServiceActor
}
