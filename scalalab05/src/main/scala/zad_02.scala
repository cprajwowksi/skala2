def deStutter[A](list: List[A]): List[A] = {
    val numberFunc = list.foldLeft(List[A]())(_)
    val wynik2 = numberFunc(pomocnicza)
    wynik2
}
def pomocnicza[B](xs: List[B], x: B): List[B]={
        if (xs.headOption==None){
            xs:+ x
        }
        else if(xs.last != x){
            xs:+ x
        }
        else{
            xs
        }
}

@main def zad_02: Unit = {
    val l = List(0,1,1,1,1,1,1, 2, 4, 4, 4, 1, 3)
    // val wynik = assert( deStutter(l) == List(1, 2, 4, 1, 3) ) // ==> true
    val wynik = deStutter(l)
    println(wynik)
}