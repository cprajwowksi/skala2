case class PracaLiteracka(
  tytuł: String,
  autor: String,
  punkty: Int,
  
) {
  require(
    // upewniamy się, że składowe PracyLiterackiej są „sensowne”
    tytuł.trim() != "" &&
    autor.trim() != "" &&
    punkty >= 0  
  )
}

case class Wynik(
  miejsce: Int,
  tytuł: String,
  autor: String,
  punkty: Int
) {
  require(
    // upewniamy się, że składowe Wyniku są „sensowne”
    miejsce >= 0 &&
    tytuł.trim() != "" &&
    autor.trim() != "" &&
    punkty >= 0
  )
}

def praca(l: List[PracaLiteracka]): List[Wynik] ={
    val wynik = l
                .groupBy({
                    case PracaLiteracka(tytuł, autor, punkty) => punkty
                }).toList
                .sortBy({
                    case (a,b) => a
                }).reverse
                .zipWithIndex
                .flatMap({
                    case (x, y) => (x._2.map(x => Wynik(y+1,x.tytuł,x.autor,x.punkty)))
                })
    
    wynik
}

@main def zad_botv2: Unit = {
val prace = List(
  PracaLiteracka("Adam Mickiewicz", "Pan Tadeusz", 17),
  PracaLiteracka("Henryk Sienkiewicz", "Quo Vadis", 12),
  PracaLiteracka("Bolesław Prus", "Lalka", 14),
  PracaLiteracka("Adam Mickiewicz", "Dziady", 5),
  PracaLiteracka("Adam Mickiewicz", "Dziadycz3", 10),
  PracaLiteracka("Juliusz Słowacki", "Balladyna", 10),
  PracaLiteracka("Bolesław Leśmian", "Ludzie bezdomni", 8),
  PracaLiteracka("Stanisław Lem", "Solaris", 9),
  PracaLiteracka("Wisława Szymborska", "Wiersze wybrane", 9)
)
    val wynik = praca(prace)
    println(wynik)
}

