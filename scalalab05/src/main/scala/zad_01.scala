def isOrdered[A](leq: (A, A) => Boolean)(l: List[A]): Boolean = {
  if (l.tail.headOption==None){
    return true
  }
  else if (leq(l.head, l.tail.head)){
    return isOrdered(leq)(l.tail)
  }
  else{
    return false
  }
}

@main def zad_01: Unit = {
    val lt = (m: Int, n: Int) => m < n
    val lte = (m: Int, n: Int) => m <= n
    val lista = List(1, 2, 2, 5)
    println(isOrdered(lt)(lista)) // ==> false
    println(isOrdered(lte)(lista)) // ==> true

}