package lab01

/*
  Funkcja „obramuj” „zdefiniowana” poniżej wykorzystuje dwa parametry
    - „napis” potencjalnie może mieć kilka linijek (patrz przykład)
    - „znak”, z którego należy zbudować ramkę
*/
def obramuj(napis: String, znak: Char): String = {
  // definiujemy funkcję obramowującą
  
    val tablica = napis.split("\n")
    val ramka = s"$znak" * 6
    val wynik = ramka
    wynik
}

@main def zad_02: Unit = {
  val wynik = obramuj("alaaa\nma\nkota", '*')
  println(wynik)
  /*
    Efektem powino być coś podobnego do:

    ********
    * ala  *
    * ma   *
    * kota *
    ********

  */
}

