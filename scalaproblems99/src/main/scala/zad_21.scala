def myInsert[A](l: List[A], el: A, n: Int, acc: List[Any]=List()): List[Any]={
    (n,l) match {
        case (0,Nil) => acc.reverse
        case (_,Nil) => el::acc.reverse
        case (0,head::Nil)=> (el::(head::acc)).reverse
        case (0,head :: tail) => ((el::acc).reverse)++l
        case (_,head :: tail) => myInsert(tail,el,n-1,(head::acc))
        
        
    }
}

@main def zad_21: Unit ={
    val lista = List(1,2,3,4,5,6,7,8)
    val wynik = myInsert(lista,10,7)
    println(wynik)
}
