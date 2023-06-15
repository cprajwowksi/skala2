package jp1.akka.lab14

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Actor.Receive
import akka.actor.Props
import akka.actor.ActorSystem
import scala.concurrent.duration.*
import scala.util.Random

// val random = new Random()
// val liczba = random.nextInt(100) + 1



case object Strzał

class SilaWyzsza(zamekA: ActorRef, zamekB: ActorRef) extends Actor with ActorLogging {
  def receive: Receive = {
    case Strzał =>
      zamekA ! Strzał
      zamekB ! Strzał
  }
}
class Zamek extends Actor with ActorLogging {
  def receive: Receive = {
    case Strzał =>
      val moiObrońcy = for (i <- 1 to 100) yield context.actorOf(Props[Obrońca](), s"obronca${i}-${self.path.name}") 
      context.become(zamekZLudzmi(moiObrońcy.toList))
  }
  def zamekZLudzmi(mojaLista: List[ActorRef]): Receive = {
    case Strzał => 
      if (mojaLista.length < 1){
        log.info(s"${self.path.name} skapitulował!")
        context.system.terminate()
      }
      if (sender().path.name == "bog"){
        for (i <- 0 to (mojaLista.length-1)) yield mojaLista(i) ! Strzał
      } else {
        val random = new Random()
        val liczba = random.nextInt(100) + 1
        val liczba2 = random.nextInt(100)
        if (liczba2 > 70) {
          val wynik = mojaLista
                        .zipWithIndex
                        .filter((a, b) => b!=liczba)
                        .map((a, b) => a)
          log.info(s"${self.path.name} ma ${wynik.length} osob")
          context.become(zamekZLudzmi(wynik))
        } 
      }
  }
}

class Obrońca extends Actor with ActorLogging {
  def receive: Receive = {
    case Strzał => 
      if (sender().path.name == "ZamekA"){
        val selection = context.actorSelection("/user/ZamekB")
        selection ! Strzał
    } else {
        val selection = context.actorSelection("/user/ZamekA")
        selection ! Strzał
    }
      
  }
}

@main def go: Unit = {
  val system = ActorSystem("system")
  // żeby planista mógł działać „w tle” potrzebuje puli wątków:
  implicit val executionContext = system.dispatcher

  // tworzymy zamki
  val zamekA = system.actorOf(Props[Zamek](), "ZamekA")
  val zamekB = system.actorOf(Props[Zamek](), "ZamekB")
  val bog = system.actorOf(Props[SilaWyzsza](SilaWyzsza(zamekA,zamekB)), "bog")
  // wczytujemy konfigurację aplikacji z „resources/application.conf”
  // i odwołujemy się do jej elementu, wyrażonego w milisekundach
  val config = system.settings.config
  val delay = config.getInt("planista.delay").milli

  system.scheduler.scheduleWithFixedDelay(
    delay,
    delay,
    bog,
    Strzał
  )

  // Uwaga! Oczywiście powyższy planista kontaktuje się jedynie
  // z jednym z zamków!!!
}
