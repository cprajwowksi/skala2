package lab04

def ciąg(n: Int): Int = {
    def pomocnicza(n: Int, acc0: Int=2, acc1: Int=1): Int = {
      n match {
        case 0 => acc0
        case 1 => acc1
        case _ => pomocnicza(n-1,acc1,acc0+acc1)
      }
    }
    pomocnicza(n)
}

@main def zadanie_01(n: Int): Unit = {
  println(ciąg(n))
}
