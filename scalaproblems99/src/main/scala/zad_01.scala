def myLast[A](list: List[A]): A = {
  list match {
    case head :: Nil => head 
    case head :: tail => myLast(tail) 
    case _ => list.head
  }
}

@main def zad_01: Unit = {
  val lista = List(1)
  println(myLast(lista))
}
