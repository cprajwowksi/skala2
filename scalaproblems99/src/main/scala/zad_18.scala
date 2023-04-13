def mySlice[A](begIndex: Int, endIndex: Int, l: List[A]): List[A] ={
    myDrop(myTake(l,endIndex),begIndex)
}

def myTake[A](l: List[A], n: Int, acc: List[A]=List()): List[A] = {
    (n,l) match{
        case (0, _) => acc.reverse
        case (_,Nil) => acc.reverse
        case (_,head::tail) => myTake[A](tail,n-1,head :: acc)
    }
}
def myDrop[A](l: List[A],n: Int, acc: List[A]=List()): List[A] = {
    (n,l) match {
        case (0, _) => l
        case (_,Nil) => l
        case (_,head::tail) => myDrop(tail,n-1)
    }
}

@main def zad_18: Unit ={
    val wynik = mySlice(3,7, List("a","b","c","d","e","f","g","h","i","j","k"))
    println(wynik)
}