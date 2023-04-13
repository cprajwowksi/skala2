package lab06

def freqMax[A](list: List[A]): (Set[A],Int) = {
  val wynik = list.groupBy(n => n)
  val values = wynik.values.toSeq
  val sorting = values.sortWith(_.size>_.size)
  val takeWhile = sorting.takeWhile(_.size>=sorting.head.size)
  val map = takeWhile.flatMap(x => x).toSet
  println(map)
  (map, sorting.head.size)
}

@main def zadanie_02: Unit = {
  val wynik = freqMax(List(1,2,3,4,1,4))
  println(wynik)
  println("Testujemy zadanie 2")
}
