package lab04

def sumuj(l: List[Option[Double]]): Option[Double] = {
  l match {
    case ogon::głowa => ???
  }
    None
}

@main def zadanie_03: Unit = {
  // Program powinien umożliwić „sprawdzenie” działania
  // funkcji „sumuj”.
  val lista = List(Some(4.0), Some(-3.0), None, Some(1.0), Some(0.0))
  sumuj(lista) == Some(5.0) // true
}
