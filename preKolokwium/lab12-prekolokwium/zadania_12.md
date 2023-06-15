# Laboratorium 12

## Zadanie 12.0

Zdefiniuj klasę

```scala
class Boss extends Actor with ActorLogging { /* ... */ }
```

która z poziomu programu głównego posłuży do stworzenia aktora, który po otrzymaniu komunikatu

```scala
case class Oblicz(n: Int)
```

ma za zadanie wypisać na konsoli wartość `n`-tego wyrazu ciągu Fibonacciego. W tym celu, `Boss` wykorzystuje aktora typu

```scala
class Nadzorca(cache: Map[Int, BigInt] = Map(1 -> 1, 2 -> 1), doZrobienia: Set[Int] = Set()) extends Actor with ActorLogging { /* ... */ }
```

do którego przesyła kolejne otrzymywane przez siebie komunikaty `Oblicz(k)`

`Nadzorca` otrzymując komunikat `Oblicz(k)` sprawdza, czy ma już w cache'u klucz `k`. Jeśli tak, to odsyła wartości `k` oraz `cache(k)` w komunikacie typu

```scala
case class Wynik(n: Int, fib: BigInt)
```

do `Boss`-a. W przeciwnym wypadku `Nadzorca` tworzy aktora typu

```scala
class Pracownik(k: Int) extends Actor with ActorLogging { /* ... */ }
```

którego zadaniem będzie obliczenie `k`-tego elementu ciągu.