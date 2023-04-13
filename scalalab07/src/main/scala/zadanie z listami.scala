// case class Ocena(imię: String, nazwisko: String, wdzięk: Int, spryt: Int) {
//   require(
//     // upewniamy się, że składowe Oceny są sensowne
//     imię.trim() != "" &&
//     nazwisko.trim() != "" &&
//     (0 to 20).contains(wdzięk) &&
//     (0 to 20).contains(spryt)
//   )
// }

// case class Wynik(
//   miejsce: Int,
//   imię: String,
//   nazwisko: String,
//   średniWdzięk: Double,
//   średniSpryt: Double,
//   suma: Double
// ) {
//   // upewniamy się, że składowe Wyniku są „sensowne”
//   require(
//     miejsce >= 0 &&
//     imię.trim() != "" &&
//     nazwisko.trim() != "" &&
//     średniWdzięk >= 0 && średniWdzięk <= 20 &&
//     średniSpryt >= 0 && średniSpryt <= 20 &&
//     suma == średniWdzięk + średniSpryt
//   )
// }

// def sumaWszystkichWyników[A](l: List[Ocena]): List[Wynik] = { // nagle całe zadanie xd
//   val wynik = l.groupBy(x => (x.imię,x.nazwisko)).map(x => x._2)
//   val suma4 = wynik.map(x => x.fold(Ocena(x(0).imię,x(0).nazwisko,0,0))((sum,el) => Ocena(el.imię,el.nazwisko,(sum.wdzięk+el.wdzięk/x.size),(sum.spryt+el.spryt/x.size)))).toList
//   val suma5 = suma4.map[Wynik](x => Wynik(1,x.imię,x.nazwisko,x.wdzięk,x.spryt,x.wdzięk+x.spryt))
//   val sort = suma5.sortWith(_.suma>_.suma)
//   val tablicaWyników = sort.zipWithIndex.map((wynik,index)=> Wynik(index+1,wynik.imię,wynik.nazwisko,wynik.średniWdzięk,wynik.średniSpryt,wynik.suma))
//   tablicaWyników
  
// }

// @main def zad_03: Unit = {


//   val lista = List(Ocena("a","b",0,20),Ocena("a","b",0,20),Ocena("a","b",0,20),Ocena("a","b",7,20),Ocena("a","2b",20,20),Ocena("2a","b",0,8),Ocena("2a","b",0,6),Ocena("a3","b",5,20))
//   val wynik = sumaWszystkichWyników(lista)
//   println(wynik)
// }
