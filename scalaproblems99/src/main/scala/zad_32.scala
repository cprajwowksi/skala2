def nwd(a: Int, b: Int): Int = {
    (a%b,b%a) match {
        case (_,0) => a
        case (0,_) => b
        case (_,_) => if a > b then nwd(a%b,b) else nwd(a,b%a)
    }

}

@main def zad_32: Unit = {
    println(nwd(95,80))

}