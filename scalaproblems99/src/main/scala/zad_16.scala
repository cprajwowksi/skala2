def myDrop[A](l: List[A],n: Int,acc: List[A]=List()): List[A] = {
    (l,n) match {
        case (Nil, 0) => acc
        case (head :: _, 0) => myDrop(l.tail,n,acc)
        case (head::tail, _) => myDrop(tail,n-1,head :: acc)
        case (_,_) => acc

    }
}

@main def zad_16: Unit ={
    val lista = List(1,2,3,4,5,6,7,8,9)
    println(myDrop(lista,2))
}