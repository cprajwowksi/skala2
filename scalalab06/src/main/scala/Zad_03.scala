package lab06

def difficult[A](list: List[A])(len: Int, shift: Int = 1): List[List[A]] = {
 def pomocnicza[A](list: List[A])(len: Int, shift: Int = 1, acc: List[List[A]] = List()): List[List[A]] = {
  list match {
    case l if l.size < len => acc :+ l
    case l => pomocnicza(myDrop(l,shift))(len, shift, acc :+ myTake(l,len))
  }
}
  pomocnicza(list)(len,shift)
}
def myTake[A](list: List[A],n: Int,acc: List[A]=List()): List[A] = {
  n match {
    case 0 => acc.reverse
    case _ => myTake(list.tail,n-1,list.head :: acc)
  }
}

def myDrop[A](list: List[A], n: Int, acc: List[A]=List()): List[A] = {
  n match{
    case 0 => list
    case _ => myDrop(list.tail,n-1)
  }

}
@main def zadanie_03: Unit = {
  val ( list, len, shift ) = ( List(1,2,3,4,5), 3, 1 )
  val lista = List(1,2,3,4,5)
  // difficult(list)(len, shift) == List(List(1,2,3), List(2,3,4), List(3,4,5)) // => true
  println(difficult(list)(len, shift))
  val wynik1 = myDrop(lista,4)
  val wynik2 = lista.drop(4)
  val wynik3 = lista.take(3)
  val wynik4 = myTake(lista,3)
  println(wynik1)
  println(wynik2)
  println(wynik3)
  println(wynik4)
}
