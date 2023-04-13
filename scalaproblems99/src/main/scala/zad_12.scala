def myDecode[A](l: List[A], acc: List[(Int, A)]=List()): List[Char] ={
    def myPack[A](l: List[A],acc: List[List[A]]=List(), acc2: List[A]=List()): List[List[A]] = {
        l match {
            case Nil => (acc2 :: acc).reverse
            case head :: Nil => if (acc2.headOption!=None){
                if (head == acc2.head) then myPack(l.tail,acc,head :: acc2) else acc2 :: acc}
                else{
                    myPack(l.tail, acc,head :: acc2)  
                }
            case head :: tail => if (head == tail.head) then myPack(tail,acc,head :: acc2) else myPack(tail, (head :: acc2) :: acc,List())
        }
    }
    val pack = myPack(l)
    val mapa = pack.map(x => (x.size,x.head))
    val wynik = mapa.map(x =>  x._2.toString * x._1)
    val test = wynik.flatMap(_.toString())
    test
}


@main def zad_12: Unit = {
    val lista = List(1,1,1,1,2,3,2,1)
    println(myDecode(lista))
}