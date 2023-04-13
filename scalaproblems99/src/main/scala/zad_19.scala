def myRotate[A](l: List[A], n: Int): List[A] = {
    n match {
       case 0 => l
       case _ if n > 0 => myRotate((l.head :: l.tail.reverse).reverse, n-1)
       case _ if n < 0 => myRotate((l.head :: l.tail.reverse).reverse, n+1)
    }

}

@main def zad_19: Unit ={
    val wynik = myRotate(List("a","b","c","d","e","f","g","h","i","j","k"),2)
    println(wynik)
}