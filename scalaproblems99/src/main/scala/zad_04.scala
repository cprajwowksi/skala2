def mySize[A](list: List[A], acc: Int = 0): Int = {
    list match {
        case head :: tail => mySize(tail,acc+1)
        case _ => acc
    }
}

@main def zad_04: Unit = {
    val lista = List(1,2,3,4,5,6,7,8)
    println(mySize(lista))
    println(lista.size)
}
