def myPrimeFactors(n: Int): List[Int] = {
    def pomocnicza(n: Int, acc: List[Int]=List(), licznik: Int=2): List[Int] = {
        (isPrime(licznik),n%licznik) match {
            case (true,0) => if n!=licznik then pomocnicza(n/licznik,licznik::acc,licznik=2) else licznik::acc
            case (_,_) => pomocnicza(n,acc,licznik+1)
        }
    }
    pomocnicza(n)
}

@main def zad_35: Unit = {
    val wynik = myPrimeFactors(200)
    println(wynik)
}