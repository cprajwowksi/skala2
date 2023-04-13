package lab04

def tasuj(l1: List[Int], l2: List[Int]): List[Int] = {
    def pomocnicza(l1: List[Int],l2: List[Int], acc: List[Int]=List() ): List[Int] = {
      (l1,l2) match {
        case (głowa1::ogon1,głowa2::ogon2) => {
          if (głowa1==głowa2) {
            pomocnicza(ogon1,ogon2,głowa1::acc)
           } else if (głowa1<głowa2){
            pomocnicza(ogon1,ogon2,(acc:+głowa1):+głowa2)
          }
          else{
            pomocnicza(ogon1,ogon2,(acc:+głowa2):+głowa1)
          }
        }
        case (_,_::ogon2) => acc++ogon2
        case (_::ogon1,_) => acc++ogon1
        case _ => acc
      }
    }
    pomocnicza(l1,l2)
}

@main def zadanie_02: Unit = {
  val lista1 = List(2, 4, 3, 5)
  val lista2 = List(1, 2, 2, 3, 1, 5)
  
  val wynik = tasuj(lista1, lista2)// true
  println(wynik)
}
