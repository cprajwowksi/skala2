
// import akka.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

// class Boss extends Actor with ActorLogging {
//   def receive: Receive = {
//     case Oblicz(n) => {

//       val nadzorca = context.system.actorOf(Props[Nadzorca](Nadzorca()), "nadzorca")
//       nadzorca ! Oblicz(3)
//     }
//     case Wynik(liczba1, liczba2) => {
//       log.info(s"$liczba1 to jest $liczba2")
//       if (liczba1<9) then {
//         sender() ! Oblicz(liczba1+1)
//         log.info("śle go dalej")
//       } else {
//         context.system.terminate()
//       }
      
//     }
//   }
// }

// case class Oblicz(n: Int)
// case class Wynik(n: Int, fib: BigInt)

// class Nadzorca(cache: Map[Int, BigInt] = Map(1 -> 1, 2 -> 1), doZrobienia: Set[Int] = Set()) extends Actor with ActorLogging {
//   def receive: Receive = {
//     case Oblicz(n) => {
//       if (cache.contains(n)){
//         sender() ! Wynik(n, cache(n))
//       } else{
//         val robol = context.system.actorOf(Props[Pracownik](Pracownik(n)), s"pracownik$n")
//         val wartosc1 = cache.toList.reverse.tail.reverse.toMap.keys.last
//         val wartosc2 = cache(wartosc1)
//         robol ! Wynik(wartosc1, wartosc2)
//         context.become(koniecRobotki(cache, sender()))
//       }
//     }
//   }
//   def koniecRobotki(mapka: Map[Int, BigInt], bosik: ActorRef): Receive = {
//     case Oblicz(n) => {
//       if (mapka.contains(n)){
//         sender() ! Wynik(n, mapka(n))
//       } else{
//         log.info(s"$mapka")
//         val robol = context.system.actorOf(Props[Pracownik](Pracownik(n)), s"pracownik$n")
//         robol ! Wynik(n-2, mapka(n-2))
//       }
//     }
//     case Wynik(liczba, liczbaFib) => {
//       bosik ! Wynik(liczba, liczbaFib)
//       context.become(koniecRobotki(mapka + (liczba -> liczbaFib), bosik))
//     }
//   }

// }

// class Pracownik(k: Int) extends Actor with ActorLogging { 
//   def receive: Receive = {
//     case Wynik(x, y) => {
//       context.become(aDin(y))
//       sender() ! Oblicz(x+1)
//     }
//   }
//   def aDin(liczba: BigInt): Receive = {
//     case Wynik(a, b) => {
//       sender() ! Wynik(k, liczba+b)
//     }
//   }
// }

// @main def main: Unit = {
//   val system = ActorSystem("Fibonacci")
//   val leonardo = system.actorOf(Props[Boss](), "leonardo")
//   leonardo ! Oblicz(5)
// }
