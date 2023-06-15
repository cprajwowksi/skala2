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
  case class Wyniki(w: Map[ActorRef, Option[Ocena]])
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
      val grupa = context.actorOf(Props(Grupa(zawodnicy)), "Startowa")
      grupa ! Grupa.Runda
    // Obsługa pozostałych komunikatów
    case Wyniki(acc) => 
        val lista = 
        acc.map(x => x match {
        case (zawodnik, Some(ocena)) => (zawodnik, ocena)
        case (_, None) => ("elo", Ocena(0,0,0))
        })
        val nowaLista = lista.map(x => x match {
          case (napis, Ocena(jeden, dwa, trzy)) => (napis, Ocena(jeden, dwa, trzy), jeden+dwa+trzy)
        })
        val posortowana = nowaLista.toList.sortWith(_._3>_._3)
        val doFinalu = posortowana.take(20)
        val doFinaluKoniec = doFinalu.map(x => x match {
          case (zawodnik, ocena, wynik) => zawodnik
        })
        val wylot: List[ActorRef] = doFinaluKoniec.toList
        val grupa = context.actorOf(Props(Grupa(wylot)), "Finałowa")
        grupa ! Grupa.Runda

    case Stop =>
      log.info("kończymy zawody...")
      context.system.terminate()
  }
}
