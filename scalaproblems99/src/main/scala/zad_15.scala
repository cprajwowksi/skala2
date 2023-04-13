def myDuplicate[A](n: Int, l: List[A], acc: List[List[A]]= List()): List[List[A]] = {
   def pomocnicza[A](n: Int,l: List[A], acc: List[A] = List()): List[A] = {
        n match {
            case 0 => acc
            case _ => pomocnicza(n-1, l, l.head :: acc)
        }
   }
   l match {
    case Nil => acc.reverse
    case _ => myDuplicate(n, l.tail, pomocnicza(n, l) :: acc )
   }
   }



@main def zad_15: Unit = {
    val lista = List(1,2,3)
    println(myDuplicate(3,lista))
}