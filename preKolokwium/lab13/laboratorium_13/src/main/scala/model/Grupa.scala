package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef, ActorLogging}

object Grupa {
  case object Runda
  // Zawodnicy mają wykonać swoje próby – Grupa
  // kolejno (sekwencyjnie) informuje zawodników
  // o konieczności wykonania próby i „oczekuje”
  // na ich wynik (typu Option[Ocena])
  case object Wyniki
  // Polecenie zwrócenia aktualnego rankingu Grupy
  // Oczywiście klasyfikowani są jedynie Zawodnicy,
  // którzy pomyślnie ukończyli swoją próbę
  case class Wynik(ocena: Option[Ocena])
  // Informacja o wyniku Zawodnika (wysyłana przez Zawodnika do Grupy)
  // np. Wynik(Some(Ocena(10, 15, 14)))
  // Jeśli zawodnik nie ukończy próby zwracana jest wartość Wynik(None)
  case object Koniec
  // Grupa kończy rywalizację
}
class Grupa(zawodnicy: List[ActorRef]) extends Actor with ActorLogging {
  def receive: Receive = {
    case Grupa.Runda => {
      zawodnicy.head ! Zawodnik.Próba
      context.become(rozpoczęta(zawodnicy.tail, sender()))
    }
  }
  def rozpoczęta(zawodnicyv2: List[ActorRef], organizator: ActorRef,  acc: Map[ActorRef, Ocena] = Map()): Receive = {
    case Grupa.Wynik(o) => {
      if (o == None) {
        if (zawodnicyv2.length == 0){
          log.info("Koniec")
          organizator ! Organizator.Wyniki(acc)
        } else {
          zawodnicyv2.head ! Zawodnik.Próba
        }
      } else {
        if (zawodnicyv2.length == 0) {
          log.info("Koniec!!")
          organizator ! Organizator.Wyniki(acc)
        } else {
          zawodnicyv2.head ! Zawodnik.Próba
          val wynik = o match {
            case Some(x) => x
            case _ => Ocena(0,0,0)
          }
          context.become(rozpoczęta(zawodnicyv2.tail, organizator, acc + ((sender(), wynik))))
        }
      }
  }
}
}