package lab06

def subseq[A](list: List[A], begIdx: Int, endIdx: Int): List[A] = {
  list.take(endIdx+1).drop(begIdx)
}

@main def zadanie_01: Unit = {
  val l = List(1,2,3,4,5)
  println(subseq(l,2,4))  // List(1,2,3)
 

  println("Testujemy zadanie 1")
}
