def myIsPalindrome[A](l: List[A]): Boolean = {
    l match {
        case Nil => true
        case head :: Nil => true
        case head :: tail => if (head == myLast(tail)) then myIsPalindrome(myReverse(tail).tail) else false
    }
}

@main def zad_06: Unit = {
    val lista = List(1,2,3,2,1)
    println(myIsPalindrome(lista))
}

