case class Ocena(imię: String, nazwisko: String, wdzięk: Int, spryt: Int) {
  require(
    // upewniamy się, że składowe Oceny są sensowne
    imię.trim() != "" &&
    nazwisko.trim() != "" &&
    (0 to 20).contains(wdzięk) &&
    (0 to 20).contains(spryt)
  )
}

case class Wynik(
  miejsce: Int,
  imię: String,
  nazwisko: String,
  średniWdzięk: Double,
  średniSpryt: Double,
  suma: Double
) {
  // upewniamy się, że składowe Wyniku są „sensowne”
  require(
    miejsce >= 0 &&
    imię.trim() != "" &&
    nazwisko.trim() != "" &&
    średniWdzięk >= 0 && średniWdzięk <= 20 &&
    średniSpryt >= 0 && średniSpryt <= 20 &&
    suma == średniWdzięk + średniSpryt
  )
}

def podział(l: List[Ocena]): List[Wynik] = {
    val podzial=l.groupBy(x => (x.imię, x.nazwisko)).map(x => x._2).toList
    val wyniki = podzial.map(x => x.foldLeft(Wynik(1,"abc","cde",0,0,0))((acc, el) => {
        val ek: Double = el.spryt
        val wd: Double = el.wdzięk
        Wynik(1,el.imię,el.nazwisko,acc.średniWdzięk+wd/x.size,acc.średniSpryt+(ek/x.size),acc.średniWdzięk+(wd)/x.size+acc.średniSpryt+(ek)/x.size)
    }))
    val sort = wyniki.sortWith(_.suma>_.suma)
    val pomysl = sort.map(x => x.suma).groupBy(x => x).map(x => x._2).toList.sortWith(_(0)>_(0)).zipWithIndex.flatMap{
        case (values,index) => values.map((_,index))
    }.map(x => x._2)
    val wynik = sort.zip(pomysl).map(x => Wynik(x._2+1,x._1.imię,x._1.nazwisko,x._1.średniWdzięk,x._1.średniSpryt,x._1.suma))
    // wziac je zzipowac potem z wynikami ez
    wynik
}

@main def zad_03v2: Unit = {
    val lista = List(Ocena("Ham","Gad",19,10),Ocena("Jan","Kowalski",13,12),Ocena("Cezary","Prajwowski",20,20),Ocena("Cezary","Prajwowski",13,10),Ocena("Ham","Gad",5,10),Ocena("On","Gad",2,1),Ocena("Ona","wan",2,1),Ocena("AlA","Sama",13,12))
    val wynik = podział(lista)
    println(wynik)

}
