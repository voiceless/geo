package service

import akka.actor.Actor
import dao.DAO
import org.json4s.DefaultFormats
import scaldi.akka.AkkaInjectable
import scaldi.{Injectable, Injector}
import spray.httpx.Json4sSupport
import spray.routing.HttpService

/**
  * Created by pbezglasnyi.
  */
class GeoServiceActor(implicit injector: Injector) extends HttpService with Json4sSupport with Actor with AkkaInjectable  {

  def actorRefFactory = context

  def receive = runRoute(routes)

  override implicit def json4sFormats = DefaultFormats

  val dao = inject[DAO]

  val routes = pathPrefix("near") {
    get {
      parameters('lon.as[Double], 'lat.as[Double], 'user_id.as[Int]) {
        (lon, lat, user_id) => {
          ctx => ctx.complete(dao.isNear(lat, lon, 1).toString)
        }
      }
    }
  } ~
    pathPrefix("count") {
      parameters('lat.as[Int], 'lon.as[Int]) {
        (lat, lon) => {
          ctx => ctx.complete(dao.countLabelsInInGrid(lon, lat).toString)
        }
      }
    }

}


