import akka.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

class Boss extends Actor with ActorLogging {
    def receive: Receive = {
        case Oblicz(n) => 
            val nadzorca = context.actorOf(Props[Nadzorca](Nadzorca()), "nadzorca")
            nadzorca ! Oblicz(3)
            context.become(bossik(n))
    }
    def bossik(liczba: Int): Receive = {
        case Wynik(n, fib) => 
            if (n == liczba){
                log.info(s"Mam liczbę, $n-ta liczba fibonacciego to $fib")
                context.system.terminate()
            } else {
                sender() ! Oblicz(n+1)
            }
    }
}

case class Oblicz(n: Int)

class Nadzorca(cache: Map[Int, BigInt] = Map(1 -> 1, 2 -> 1), doZrobienia: Set[Int] = Set()) extends Actor with ActorLogging {
    def receive: Receive = {
        case Oblicz(n) => 
            if (cache.contains(n)){
                sender() ! Wynik(n, cache(n))
            } else {
                val robol = context.actorOf(Props[Pracownik](Pracownik(n)), s"robol${n}")
                robol ! Wynik(n-2, cache(n-2))
                context.become(rozszerzam(cache))
            }
    }
    def rozszerzam(cachev2: Map[Int, BigInt]): Receive = {
        case Oblicz(n) => {
               if (cachev2.contains(n)){
                sender() ! Wynik(n, cachev2(n))
            } else {
                val robol = context.actorOf(Props[Pracownik](Pracownik(n)), s"robol${n}")
                robol ! Wynik(n-2, cachev2(n-2))
                context.become(rozszerzam(cachev2))
            }
        }
        case Wynik(n, fib) => {
            val mojBoss = context.actorSelection("/user/leonardo")
            mojBoss ! Wynik(n, fib)
            context.become(rozszerzam((cachev2 + (n -> fib)) ))
        }
    }

}

case class Wynik(n: Int, fib: BigInt)

class Pracownik(k: Int) extends Actor with ActorLogging {
    def receive: Receive = {
        case Wynik(n, fib) =>
            context.become(czekamNaNumer2(fib)) 
            sender() ! Oblicz(n+1)
    }
    def czekamNaNumer2(fibonac: BigInt): Receive = {
        case Wynik(n2, fib2) => 
            // val mojNadzorca = context.actorSelection("/user/nadzorca")
            sender() ! Wynik(k, fib2+fibonac)
    }
}

@main def zad12v2: Unit = {
  val system = ActorSystem("Fibonacci")
  val leonardo = system.actorOf(Props[Boss](), "leonardo")
  leonardo ! Oblicz(11)
}
