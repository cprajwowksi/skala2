
import akka.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}
import scala.util.Random
case class Sprawdz(głos: Option[Int])
case class Przyjmij(ludzie: Set[ActorRef])
case class Głos(numerListy: Int)
case object Zagłosuj
class PKW extends Actor with ActorLogging {
  def receive: Receive = {
    case Przyjmij(obywatele) => 
      val komisje = (for (i <- 1 to obywatele.toList.length/10) yield context.actorOf(Props[Komisja](Komisja()), s"komisja-nr.$i")).toList
      // val wysylanko = for (i <- 1 to komisje.length) yield 
      val wysylanko = komisje.zipWithIndex
      wysylanko.foreach((x, b) => {
        x ! Przyjmij(obywatele.slice(0*b+1, 10*b+1))
      })
    case Głos(nrListy) => 
      context.become(zliczam(List(nrListy)))
  }
  def zliczam(acc: List[Int] = List()): Receive = {
    case Głos(nrListy) => 
      context.become(zliczam(nrListy :: acc))
      
      if (acc.length == 100) {
        val wynik = acc
                      .groupBy(x => x)
                      .map((x,b) => (x,b.length))
                      .toList
                      .sortBy((x,b) => b)
                      .reverse
        val maxik = wynik.maxBy((x,b) => b)._2
        val rezult = wynik
                      .takeWhile((x,b) => b==maxik)
                      .map((x,b) => s"\n 1. komisja-nr.$x \n")
                      .toString()
        val drugiWynik = wynik
                            .zipWithIndex
                            .dropWhile((x,b) => x match {case (a,b) => b == maxik})
                            .map((x,c) => s"${c+1}. komisja-nr.${x match { case (a,b) => a}} \n")
                            .toString()             
        println(drugiWynik)
        println(rezult)
        
      }
  }
  //po prostu wielkosc Listy obywateli, czyli nowy kontext i ez
}

class Obywatel extends Actor with ActorLogging{
  def receive: Receive = {
    case Zagłosuj => 
      val random = new Random()
      val liczba = random.nextInt(100) + 1
      val liczba2 = random.nextInt(10)
      if (liczba>10){
        sender() ! Sprawdz(Some(liczba2))
      } else {
        sender() ! Sprawdz(None)
      }
  }
}

class Komisja extends Actor with ActorLogging{
  def receive: Receive = {
    case Przyjmij(obywatele) => 
      obywatele.foreach(x => x ! Zagłosuj)
    case Sprawdz(nrGlosu) => 
      if (nrGlosu == None){
        sender() ! Zagłosuj
      } else {
        val pkiw = context.actorSelection("/user/PKW")
        val wynik = nrGlosu match { 
          case Some(x) => x
          case None => 1
        }
        pkiw ! Głos(wynik)
      }
  }
}


@main def main: Unit = {
  val system = ActorSystem("WYBORY")
  val ludziki = for (i <- 1 to 100) yield system.actorOf(Props[Obywatel](Obywatel()), s"obywatel-nr.$i")
  val leonardo = system.actorOf(Props[PKW](), "PKW")
  leonardo ! Przyjmij(ludziki.toSet)
}
