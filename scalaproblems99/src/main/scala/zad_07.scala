def myFlatten[A](l: List[A], acc: List[Any]=List()): List[Any] ={
    l flatMap {
        case ms: List[_] => myFlatten(ms)
        case e => List(e)
    }
}

def myFlatten2(l: List[Any]): List[Any] = {
    def _flatten(res: List[Any], rem: List[Any]):List[Any] = rem match {
        case Nil => res
        case (h:List[_])::Nil => _flatten(res, h)
        case (h:List[_])::tail => _flatten(res:::myFlatten2(h), tail)
        case h::tail => _flatten(res:::List(h), tail)
    }
    _flatten(List(), l)
}

@main def zad_07: Unit = {
    println(myFlatten2(List(List(1, 1), 2, List(3, List(5, 8)))))
}
