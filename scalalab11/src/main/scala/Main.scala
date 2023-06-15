
import akka.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

class Boss extends Actor with ActorLogging {
  def receive: Receive = {
    case Oblicz(k) => 
      val nadzorca = context.actorOf(Props(new Nadzorca()), "nadzorca")
      nadzorca ! Oblicz(3)
      context.become(nowyBoss(k))
    case Wynik(n, cache) => 
      log.info(s"$n $cache")
 }
  def nowyBoss(limit: Int): Receive = {
    case Wynik(n, cache) => 
      if n==limit then log.info(s"$n $cache")
      if (n<limit){
        log.info(s"lece!? ${n+1}")
        sender() ! Oblicz(n+1)
      }
      
  }
}

class Nadzorca(cache: Map[Int, BigInt] = Map(1 -> 1, 2 -> 1), doZrobienia: Set[Int] = Set()) extends Actor with ActorLogging {
  def receive: Receive = {
   case Oblicz(n) => 
    if (cache.contains(n)) {
        sender() ! Wynik(n, cache(n))
    }
      else {
        
         val pracownik = context.actorOf(Props(Pracownik(n)), s"pracownik$n")
         pracownik ! Wynik(cache.keys.last, cache.values.last)
         context.become(nadzorcaBossa(cache, sender()))
      }
  
  }
  def nadzorcaBossa(cachev2: Map[Int, BigInt], boss: ActorRef): Receive = {
    case Oblicz(n) => 
    if (cachev2.contains(n)) {
        sender() ! Wynik(n, cachev2(n))
    }
     else {
         log.info(s"Odbiłem $n i mam $cachev2")
         val pracownik = context.actorOf(Props(Pracownik(n)), s"pracownikv$n")
         pracownik ! Wynik(cachev2.max._1, cachev2.max._2)
      }
    case Wynik(n, fib) => 
      context.become(nadzorcaBossa(((n, fib) :: cachev2.toList.reverse).reverse.toMap, boss))
      boss ! Wynik(n, fib)
  }

}
//tworzy pracownika ktory kazdy wysyla do nadzorcy 
class Pracownik(k: Int) extends Actor with ActorLogging { 
  def receive: Receive = {
    case Wynik(lastv, lastFib) => 
      if (lastv+1==k) {
        log.info(s"dostałem $lastFib i wysyłam ${lastv-1}")
        sender() ! Oblicz(lastv-1)
        context.become(czekamNaWynik(lastFib))
       }
       else{
        log.info(s"Odbiłem $k mimo że mam $lastv")
       }
}

  def czekamNaWynik(fib: BigInt): Receive = {
      case Wynik(last2, lastFib2) => {
          log.info(s"Bede słał ${lastFib2+fib}")
          sender() ! Wynik(k, lastFib2+fib)
          context.become(receive)
      }
  }
}
case class Oblicz(n: Int)
case class Wynik(n: Int, fib: BigInt)

@main def main: Unit = {
    val system = ActorSystem("Fibonacci")
    val leonardo = system.actorOf(Props[Boss](), "leonardo")
    leonardo ! Oblicz(11)
}
