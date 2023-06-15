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
      val grupa = context.actorOf(Props[Grupa](Grupa(zawodnicy)), "grupaQuali")
      grupa ! Grupa.Runda
    // case "eliminacje" => 
    //   val grupaEliminacji = context.actorSelection("/user/grupaQuali")
    //   grupaEliminacji ! Runda

    case Wyniki(mapa) => 
      val wynik = mapa
                  .toList
                  .map((a, b) => (a, b match {
                    case Some(Ocena(adin,dwa,tri)) => Ocena(adin,dwa,tri)
                    case _ => None
                  }))
                  .filter((a,b) => b!=None)
                  .sortBy((a, b) => b match {
                    case Ocena(jeden, dwa, trzy) => dwa
                  })
                  .sortBy((a, b) => b match {
                    case Ocena(jeden, dwa, trzy) => jeden
                  })
                  .sortBy((a, b) => b match {
                    case Ocena(jeden, dwa, trzy) => jeden+dwa+trzy
                  })
                  .reverse
      if (mapa.toList.length > 25){
        val wysylaj = wynik
                        .map((a, b) => a)
                        .take(20)
      }  

      val wynikv2 = wynik
                      .map((a, b) => (a.path.name, b))
                      .groupBy((a, b) => b)
                      .toList
                      .sortBy((a, b) => a match {
                        case Ocena(jeden, dwa,trzy ) => dwa
                      })
                      .sortBy((a, b) => a match {
                        case Ocena(jeden, dwa,trzy ) => jeden
                      })
                      .sortBy((a, b) => a match {
                        case Ocena(jeden, dwa,trzy ) => jeden+dwa+trzy
                      }).reverse
                      .map((a, b) => (a match { case Ocena(z,x,c) => z+x+c},b))
                      .zipWithIndex
                      .map((a, b) => (a, b+1))
                      .map((a, b) =>
                        val reszta = a match {
                          case (x,y) =>  
                              if (y.length == 1){
                                val fajnie = y(0)._2 match {
                                  case Ocena(j,k,l) => s"$j-$k-$l"
                                }
                                s"${y(0)._1} - $fajnie = $x \n"
                              } else {
                                s"wynik $x dla ${y}\n"
                              }
                            // s"wynik $x dla $y"
                        }
                        s"$b.$reszta "
                        )
                      // .groupBy((a,b) => a match {
                      //   case (_, c) => c
                      // })
                      // .toList
                      // .sortBy((a, b) => a match {
                      //   case Ocena(jeden, dwa, trzy) => jeden+dwa+trzy
                      // })
                    

        
                  

      log.info(s"$wynikv2")
    // Obsługa pozostałych komunikatów

    case Stop =>
      log.info("kończymy zawody...")
      context.system.terminate()
  }
}
