def ilośćPrime(n: Int): Int ={
    def pomocnicza(n: Int, beg: Int=1, acc: Int=0): Int = {
        if (n!=beg) {
          nwd(n,beg) match {
            case 1 => pomocnicza(n,beg+1,acc+1)
            case _ => pomocnicza(n,beg+1,acc)
        }  
        }
        else {
            acc
        }
    }
    pomocnicza(n)
}

@main def zad_34: Unit = {
    val wynik = ilośćPrime(6)
    println(wynik)
}