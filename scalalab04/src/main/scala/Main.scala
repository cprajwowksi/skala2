@main def mainProg: Unit = {
  val lista = List(Some(4.0), Some(-3.0), None, Some(1.0), Some(0.0))
  val wynik = lista(0)+lista(1)
  println(wynik)
}
