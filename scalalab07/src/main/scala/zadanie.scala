def turniej(l: List[Ocena]): List[Wynik] = {
    val pogrupowane = l.groupBy({
        case Ocena(x,y,_,_) => (x,y)
    }).map(x => x._2)
        .map(x => x.foldLeft(Wynik(1,"abc","cde",0,0,0))({
            case (Wynik(miejsce1,_,_,sredniwdziek1,srednispryt1,suma), Ocena(imie2,nazwisko2,wdzięk,spryt)) => {
                Wynik(miejsce1,imie2,nazwisko2,sredniwdziek1+(wdzięk.toDouble)/x.size,srednispryt1+(spryt.toDouble)/x.size,sredniwdziek1+(wdzięk.toDouble)/x.size+srednispryt1+(spryt.toDouble)/x.size)
            }
        } )).toList
    val miejsca = pogrupowane.groupBy({
        case Wynik(_,_,_,_,_,x) => x
    }).toList.sortWith(_._1>_._1).map(x => x._2).zipWithIndex.flatMap({
        case (x,y) => x.map(x=>Wynik(y+1,x.imię,x.nazwisko,x.średniWdzięk,x.średniSpryt,x.suma))
    }
    )
    println(miejsca)
    Nil
}

@main def zad_03v3: Unit = {
    val lista = List(Ocena("Ham","Gad",19,10),Ocena("Jan","Kowalski",13,12),Ocena("Cezary","Prajwowski",20,20),Ocena("Cezary","Prajwowski",13,10),Ocena("Ham","Gad",5,10),Ocena("On","Gad",2,1),Ocena("Ona","wan",2,1),Ocena("AlA","Sama",13,12))
    val wynik = turniej(lista)
    println(wynik)

}