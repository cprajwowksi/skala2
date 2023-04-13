def mySplit[A](n: Int, l: List[A], acc1: List[A]= List(), acc2: List[List[A]] = List()): List[List[A]] = {
    n match {
     case 0 => acc1.reverse :: (l :: acc2)   
     case _ => mySplit(n-1,l.tail, l.head :: acc1)
    }
}


@main def zad_17: Unit ={
    val wynik = mySplit(3, List("a","b","c","d","e","f","g","h","i","j","k"))
    println(wynik)
}