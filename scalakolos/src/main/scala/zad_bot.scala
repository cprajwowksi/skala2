case class AnalizaSkładu(
  liczbaZawodników: Int,
  sumaNumerówKoszulek: Int,
  największyNumerKoszulki: Int,
  zawodnicyZNumerem10: List[String],
  wszyscyZawodnicy: List[(String, Int)],
  zawodnicyONumerzeParzystym: List[String]
)

def analizaSkładów(l: List[List[(String,Int)]]): Unit = {
    val size = l.map(x => (x,x.size,
    x.foldLeft(0)({ // liczba zawodników
        case (acc,b) => acc+b._2 // numery koszulek
    }), x.maxBy({ // najwiekszy numer
        case (a,b) => b
    })._2,x.filter({ //zawodnicy z numerem 10
        case (a, b) => b==10
    }), // zawodnicy o parzystej
    (x.filter({
        case (a, b)=> b%2==0
    }).map({
        case (a,b) => a
    })), // zawodnicy o parzystej
    (x.map({
        case (a,b) => a
    }).map(a => a.split(" ").toList)
    .map(a => (a(0),a(1))).sortWith(_._2<_._2)
    .map({
        case (a,b) => (s"$a $b")
    })
    
    )
    ))
    println(size)
    println()
}

@main def zad_bot: Unit = {
    val skład1 = List(
  ("Jan Kowalski", 1),
  ("Adam Nowak", 2),
  ("Piotr Nowicki", 3),
  ("Cezary Prajwowski", 10),
  ("Kamil Lewandowski", 4),
  ("Marek Szymański", 5)
    )
    val skład2 = List(
  ("Bartosz Nowak", 1),
  ("Krzysztof Lewandowski", 2),
  ("Grzegorz Kowalski", 3),
  ("Tomasz Nowicki", 4),
    ("Michał Szymański", 5)
    )
    val skład3 = List(
    ("Maciej Lewandowski", 1),
    ("Tomasz Kowalski", 2),
    ("Jan Nowicki", 3),
    ("Kacper Szymański", 4),
    ("Bartłomiej Nowak", 5)
)
    val lista = (List(skład1,skład2,skład3))
    val wynik = analizaSkładów(lista)
}
