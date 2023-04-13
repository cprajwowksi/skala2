def myRemove[A](l: List[A],n: Int, acc: List[A]= List()): List[A] ={
    (n,l) match {
        case (0,Nil) => acc.reverse
        case (_,Nil) => acc.reverse
        case (0,head :: tail)=> (tail ++ acc).reverse
        case (_,head:: tail) => myRemove(tail,n-1,head :: acc)
    }
}


@main def zad_20: Unit = {
    val lista = List(1,2,3)
    val wynik = myRemove(lista,4)
    println(wynik)
}
