import akka.actor.ActorSystem
import akka.io.IO
import module.AppModule
import scaldi.akka.AkkaInjectable
import service.GeoServiceActor
import spray.can.Http

/**
  * Created by pbezglasnyi.
  */
object Boot extends App with AkkaInjectable {

//  System.setProperty("hadoop.home.dir", "C:\\winutil\\")
  System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.6.0")

  val labelsLocation = args(0)
  val gridLocation = args(1)

  implicit val module = new AppModule(labelsLocation, gridLocation)

  implicit val system = inject[ActorSystem]

  val service = injectActorRef[GeoServiceActor]

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)


}
