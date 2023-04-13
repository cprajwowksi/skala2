def myRange(beg: Int, end: Int, acc: List[Int]=List()): List[Int]={
    beg match {
        case _ if beg==end => (beg::acc).reverse
        case _ => myRange(beg+1,end,(beg::acc))
    }
}

@main def zad_22: Unit = {
    println(myRange(4,9))
}
