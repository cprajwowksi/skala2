package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef, ActorLogging, Props}

val akkaPathAllowedChars = ('a' to 'z').toSet union
  ('A' to 'Z').toSet union
  "-_.*$+:@&=,!~';.)".toSet

object Organizator {
  case object Start
  // rozpoczynamy zawody – losujemy 50 osób, tworzymy z nich zawodników
  // i grupę eliminacyjną
  case object Runda
  // polecenie rozegrania rundy (kwalifikacyjnej bądź finałowej) –  wysyłamy Grupa.Runda
  // do aktualnej grupy
  case object Wyniki
  // polecenie wyświetlenia klasyfikacji dla aktualnej grupy
  case class Wyniki(w: Map[ActorRef, Ocena])
  // wyniki zwracane przez Grupę
  case object Stop
  // kończymy działanie
}

class Organizator extends Actor with ActorLogging {
  // importujemy komunikaty na które ma reagować Organizator
  import Organizator._

  def receive: Receive = {
    case Start =>
      // tworzenie 50. osób, opdowiadających im Zawodników
      // oraz Grupy eliminacyjnej
      val zawodnicy = List.fill(50) {
        val o = Utl.osoba()
        context.actorOf(Props(Zawodnik(o)), s"${o.imie}-${o.nazwisko}" filter akkaPathAllowedChars)
      }
      val grupa = context.actorOf(Props(Grupa(zawodnicy)), "grupaEliminacje")
      grupa ! Grupa.Runda
      // ...

    // Obsługa pozostałych komunikatów
    case Organizator.Wyniki(mapka) => {
      val wynik2 = mapka.toList
       .sortBy((a, b) => b match {
        case Ocena(adin, dwa ,tri) => adin+dwa+tri
      })
        .reverse
        .take(20)
        .map((a,b) => a)
        .toList
      val grupa = context.actorOf(Props(Grupa(wynik2)), "grupaFinal")
      grupa ! Grupa.Runda
      context.become(koniec)
    }
    case Stop =>
      log.info("kończymy zawody...")
      context.system.terminate()
  }
  def koniec: Receive = {
    case Organizator.Wyniki(mapka2) => {
        val wynikEnd = 
          mapka2.map((a, b) => (a.path.name, b))

          .groupBy((a, b) => b match {
            case Ocena(adin, dwa, tri) => (adin,dwa,tri)
          }).toList
          .sortBy((a, b) => a match {
            case (adin, dwa, tri) => dwa
          })
           .sortBy((a, b) => a match {
            case (adin, dwa, tri) => adin
          })
          .sortBy((a, b) => a match {
            case (adin, dwa, tri) => adin+dwa+tri
          }).reverse
          .map((a,b) => b)
          .zipWithIndex
          .map((a, b) => (a, b+1))
          .map((a, b) => s"$b. ${a.keys} z oceną ${a.values}")
          // .map((a, b) => s"$b. ${a._1} z")
          // .sortBy((a, b) => a)
          // .reverse
          // .zipWithIndex
          // .map((a, b) => (a._2, b+1))
          // .map((a, b) => a)
          


        log.info(s"$wynikEnd")
        
  }
}
}


