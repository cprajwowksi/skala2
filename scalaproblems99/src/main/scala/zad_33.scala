def isCoPrime(a: Int, b: Int): Boolean =  {
    nwd(a,b) match {
        case 1 => true
        case _ => false
    }
}
@main def zad_33: Unit = {
    println(isCoPrime(5,6))
}