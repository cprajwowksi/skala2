def myEncode[A](l: List[A], acc: List[(Int, A)]=List()): List[(Int, A)] ={
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
    val paka = myPack(l)
    def pomocnicza[A](pack : List[List[A]], acc: List[(Int, A)]=List()): List[(Int,A)] = {
        pack match {
        case Nil => (acc).reverse
        case head :: Nil => ((head.size,head.head) :: acc).reverse
        case head :: tail => pomocnicza(tail,(head.size, head.head) :: acc)
    }
    }
    pomocnicza(paka)
   
}
@main def zad_10v2: Unit = {
    val lista = List(1,1,1,1,2,3,2,1)
    println(myEncode(lista))
}