def isPrime(n: Int): Boolean = {
  def pomocnicza(n: Int, acc: Int): Boolean = {
    n match {
      case 1 => true
      case _ => if acc%n!=0 then pomocnicza(n-1,acc) else false
    }
  }
  pomocnicza(n-1,n)
}

@main def zad_31: Unit = {
  println(isPrime(9))
}
