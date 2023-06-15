package jp1.akka.lab14

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Actor.Receive
import akka.actor.Props
import akka.actor.ActorSystem
import scala.concurrent.duration.*
import scala.util.Random

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
      val moiObroncy = for (i  <- 1 to 100) yield context.actorOf(Props[Obrońca](Obrońca()),s"${self.path.name}-obronca$i")
      context.become(zamekZObroncami(moiObroncy.toList))
  }
  def zamekZObroncami(obroncy: List[ActorRef]): Receive = {
    case Strzał => 
      if (obroncy.length < 1){
        log.info(s"${self.path.name} skapitulował!")
        context.system.terminate()
      }
      val random = new Random()
      val liczba = random.nextInt(100) + 1
      val liczba2 = random.nextInt(100)
      if(sender().path.name == "bog") {
        obroncy.foreach(x => x ! Strzał)
      } else {
        if (liczba>60){
          val wynik = obroncy
                        .zipWithIndex
                        .filter((a, b) => b!=liczba2)
                        .map((a, b) => a)
          log.info(s"${self.path.name} ma ${wynik.length} osob")
          context.become(zamekZObroncami(wynik))
      }}
  }
}


class Obrońca extends Actor with ActorLogging {
  def receive: Receive ={
    case Strzał => 
      if (sender().path.name == "zamekA"){
        val zamekWroga = context.actorSelection("/user/zamekB")
        zamekWroga ! Strzał
      } else {
        val zamekWroga = context.actorSelection("/user/zamekA")
        zamekWroga ! Strzał
      }
  }
}

@main def go: Unit = {
  val system = ActorSystem("system")
  // żeby planista mógł działać „w tle” potrzebuje puli wątków:
  implicit val executionContext = system.dispatcher

  // tworzymy zamki
  val zamekA = system.actorOf(Props[Zamek](), "zamekA")
  val zamekB = system.actorOf(Props[Zamek](), "zamekB")
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
