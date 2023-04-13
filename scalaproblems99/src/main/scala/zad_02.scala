def preLast[A](list: List[A]): A = {
    (list,list.tail.tail) match{
        case (head :: tail,Nil) => head
        case _ => preLast(list.tail)
    }

}

@main def zad_02: Unit = {
    val lista = List(1,2,3,4,5,6,7,8)
    println(preLast(lista))
}
