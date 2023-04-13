def myCompress[A](l: List[A],acc: List[A]=List()): List[A] = {
    l match {
        case Nil => Nil
        case head :: Nil => head :: acc 
        case head :: tail => if (head != tail.head) then myCompress(l.tail,head :: acc) else myCompress(l.tail,acc) 
    }
}

@main def zad_08: Unit = {
    val lista = List(1,1,1,1,2,3,2,1)
    println(myCompress(lista))
}