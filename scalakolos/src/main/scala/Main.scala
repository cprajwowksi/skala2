import scala.io.Source

def plik(): List[(Int,Int)] = {
  val lines = Source.fromFile("/home/pra/skala1/scalakolos/src/main/scala/test.txt").getLines.toList
  val zzipowane = lines.flatMap(x => x.split(" ").toList).zipWithIndex
  val pogrupowane = zzipowane.groupBy({
    case (x,y) => y%26
  }).toList.sortWith(_._1<_._1).tail
  val wynik = pogrupowane.map({
    case (x,y) => (x,y.tail.foldLeft(0)((a, b) => a+(b._1).toInt))
  }).sortWith(_._2>_._2)
  val miejsca = wynik.groupBy(x => x._2).toList.sortWith(_._1>_._1).zipWithIndex.toList.map(x => (x._2,x._1(1)))
  val końcowyWynik = miejsca.map({
    case (x, y) => y.map({
      case (a,_) => (a,x+1)
    })
  }).toList
  val pomocniczaSize = 0::końcowyWynik.map(x => (x.size-1))
  println(pomocniczaSize)
  val koniec = końcowyWynik.zip(pomocniczaSize).flatMap({
    case (x,y) => x.map({
      case (a,b) => (a,b+y)
    }) 
  })
  koniec
} 



@main def mainProg: Unit = {
  val wynik = plik()
  println(wynik)
}
