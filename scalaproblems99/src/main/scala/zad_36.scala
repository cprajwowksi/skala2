def myPrimeFactorsMultiplicity(n: Int): List[(Int,Int)] = {
    val mapa = myPrimeFactors(n).groupBy(x => x).map((_,x) => x).map((x) => (x(0),x.size)).toList
    mapa 
}

@main def zad_36: Unit = {
    val wynik = myPrimeFactorsMultiplicity(315)
    println(wynik)
}