package service

import akka.actor.Actor
import dao.DAO
import model.{CountResponse, NearResponse, Ok}
import org.json4s.DefaultFormats
import scaldi.Injector
import scaldi.akka.AkkaInjectable
import spray.httpx.Json4sSupport
import spray.routing.HttpService

/**
  * Created by pbezglasnyi.
  */
class GeoServiceActor(implicit injector: Injector) extends HttpService with Json4sSupport with Actor with AkkaInjectable {

  def actorRefFactory = context

  def receive = runRoute(routes)

  override implicit def json4sFormats = DefaultFormats

  val dao = inject[DAO]

  val routes = pathPrefix("near") {
    get {
      parameters('lon.as[Double], 'lat.as[Double], 'user_id.as[Int]) {
        (lon, lat, user_id) => {
          ctx => ctx.complete(NearResponse(dao.isNear(lat, lon, 1)))
        }
      }
    }
  } ~
    pathPrefix("count") {
      parameters('lat.as[Int], 'lon.as[Int]) {
        (lat, lon) => {
          ctx => ctx.complete(CountResponse(dao.countLabelsInInGrid(lon, lat)))
        }
      }
    } ~
    pathPrefix("user") {
      put {
        parameters('lon.as[Double], 'lat.as[Double]) {
          (lon, lat) => {
            ctx => {
              dao.insertUser(lon, lat)
              ctx.complete(Ok)
            }
          }
        }
      } ~
        post {
          parameters('user_id.as[Int], 'lon.as[Double], 'lat.as[Double]) {
            (user_id, lon, lat) => {
              ctx => {
                dao.updateUser(user_id, lon, lat)
                ctx.complete(Ok)
              }
            }
          }
        } ~
        delete {
          parameter('user_id.as[Int]) {
            user_id => {
              ctx => {
                dao.deleteUser(user_id)
                ctx.complete(Ok)
              }
            }
          }
        }
    }

}


