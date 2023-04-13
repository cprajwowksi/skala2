def myReverse[A](l: List[A], acc: List[A]=List()): List[A] = {
    l match { 
        case Nil => Nil
        case head :: Nil => head :: acc  
        case head :: tail => myReverse(tail,head :: acc)
    }
}

@main def zad_05: Unit = {
    val lista = List(1,2,3,4,5,6,7,8)
    println(myReverse(lista))
}
