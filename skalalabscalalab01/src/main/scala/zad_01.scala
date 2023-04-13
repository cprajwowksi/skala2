package lab01


def jestPierwsza(liczba: Int,akumulator: Int): Boolean = {
        if(liczba==2){
            true
        }
        else if ((akumulator % (liczba-1))==0){
            false
        }
        else{
            jestPierwsza(liczba-1,akumulator)
        }

    }


@main def zad_01: Unit = {
  print("Podaj liczbę naturalną: ")
  val liczba = 11
  val wynik = if jestPierwsza(liczba,liczba) then "" else " nie"
  println(s"Liczba $liczba$wynik jest liczbą pierwszą")
}
