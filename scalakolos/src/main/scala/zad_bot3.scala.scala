case class Pracownik(imie: String, nazwisko: String, pracaZespolowa: Int, kreatywnosc: Int) {
  require(
    // upewniamy się, że składowe Pracownika są sensowne
    imie.trim() != "" &&
    nazwisko.trim() != "" &&
    (0 to 10).contains(pracaZespolowa) &&
    (0 to 10).contains(kreatywnosc)
  )
}

case class Wynik1(
  miejsce: Int,
  imie: String,
  nazwisko: String,
  sredniaPracaZespolowa: Double,
  sredniaKreatywnosc: Double,
  sumaPunktow: Double
) {
  // upewniamy się, że składowe Wyniku są „sensowne”
  require(
    miejsce >= 0 &&
    imie.trim() != "" &&
    nazwisko.trim() != "" )}

def praca2(l: List[Pracownik]): List[Wynik1] = {
    val wynik = l.groupBy({
        case Pracownik(imie, nazwisko, pracaZespolowa, kreatywnosc) => (imie,nazwisko)
    }).map(x => x._2).map(x => x.foldLeft(Wynik1(0,"1","2",0,0,0))({
        case (a,b) => Wynik1(0,b.imie,b.nazwisko,
        a.sredniaPracaZespolowa+(b.pracaZespolowa.toDouble)/x.size,
        a.sredniaKreatywnosc+(b.kreatywnosc.toDouble)/x.size,
        a.sredniaPracaZespolowa+(b.pracaZespolowa.toDouble)/x.size+
        a.sredniaKreatywnosc+(b.kreatywnosc.toDouble)/x.size)
    })).toList
    .groupBy(x => x.sumaPunktow).toList.sortWith(_._1>_._1).zipWithIndex
    .map({
        case (x,y) => (y+1,x._2)
    }).flatMap({
        case (x,y) => y.map({
            case a =>(x,a.imie,a.nazwisko,a.sredniaPracaZespolowa,a.sredniaKreatywnosc,a.sumaPunktow)
        })
    })
    println(wynik)
    Nil
}

@main def zad_botv3: Unit = {
    val lista= List(
    Pracownik("Jan", "Kowalski", 8, 7),
    Pracownik("Anna", "Nowak", 9, 6),
    Pracownik("Anna", "Nowak", 3, 4),
    Pracownik("Anna", "Nowak", 3, 5),
    Pracownik("Piotr", "Wiśniewski", 7, 8),
    Pracownik("Katarzyna", "Wójcik", 6, 9),
    Pracownik("Bartosz", "Kaczmarek", 8, 8),
    Pracownik("Karolina", "Mazur", 7, 7),
    Pracownik("Tomasz", "Kalinowski", 6, 8),
    Pracownik("Magdalena", "Zając", 8, 6),
    Pracownik("Adam", "Dąbrowski", 7, 9),
    Pracownik("Izabela", "Kwiatkowska", 9, 7)
    )
    val wynik = praca2(lista)
}
