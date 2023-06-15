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
    case Grupa.Runda => 
      val organizator = sender()
      zawodnicy.head ! Zawodnik.Próba
      context.become(grupaRozpoczęta(zawodnicy.tail, zawodnicy.head, List(), organizator)) 
  }
  def grupaRozpoczęta(zawodnicy: List[ActorRef], glowa: ActorRef, acc: List[(ActorRef, Option[Ocena])], organiz: ActorRef): Receive = 
  {
    case Grupa.Wynik(ocena) => 
      if (zawodnicy.tail == Nil){
        self ! Grupa.Koniec
      } else {
        zawodnicy.head ! Zawodnik.Próba
        context.become(grupaRozpoczęta(zawodnicy.tail, zawodnicy.head, (glowa, ocena) :: acc, organiz)) 
      }

    case Grupa.Koniec => 
      // val lista: List[(String, Ocena)] = acc.map(x => x match {
      //   case (zawodnik, Some(ocena)) => (zawodnik, ocena)
      //   case (_, None) => ("elo", Ocena(0,0,0))
      // })
      // val nowaLista = lista.map(x => x match {
      //   case (napis, Ocena(jeden, dwa, trzy)) => (napis, Ocena(jeden, dwa, trzy), jeden+dwa+trzy)
      // })
      // val posortowana = nowaLista.sortWith(_._3>_._3)
      // println(posortowana)
      organiz ! Organizator.Wyniki(acc.toMap)

  }

    
  
}
