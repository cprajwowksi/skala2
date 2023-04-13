package lab03

def hipoteza(liczba: Int): Unit = {
    //@annotation.tailrec
    def pomocnicza(liczba: Int, akumulator: Int): Unit = {
        
    }
    @annotation.tailrec
    def czyPierwsza(liczba: Int,akumulator: Int): Boolean = {
        if(liczba==2){
            true
        }
        else if ((akumulator % (liczba-1))==0){
            false
        }
        else{
            czyPierwsza(liczba-1,akumulator)
        }

    }
}

@main def zad_02(): Unit = {
    // require(liczba>2)
    // require(liczba%2==0)
    val liczba = 12
    //Zdefiniuj funkcję hipoteza, która jako argument pobiera 
    //parzystą liczbę naturalną większą od 2 oraz 
    //sprawdza czy jest ona sumą dwóch liczb pierwszych. 
    //Jeżeli tak, to funkcja hipoteza powinna wypisać je na konsoli. 
    //W przeciwnym wypadku na konsoli powinien pojawić się komunikat mówiący, że 
    //liczb takich nie udało sie znaleźć.    
    hipoteza(liczba)
}
