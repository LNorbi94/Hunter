# Hunter
Egy két személyes játék, amivel lehetőség van online, illetve offline játszani.

# Összefoglalás
Kezdetben a menekülő játékos figurája középen van, míg a támadó figurái a négy sarokban
helyezkednek el. A játékosok felváltva lépnek. A figurák vízszintesen, illetve függőlegesen
mozoghatnak 1-1 mezőt, de egymásra nem léphetnek. A támadó játékos célja, hogy adott
lépésszámon (4n) belül bekerítse a menekülő figurát, azaz a menekülő ne tudjon lépni.

# Dokumentáció
A dokumentáció generálása az alábbi parancs segítségével történik:
```
  doxygen Hunter.config
```
A Documentation/html/index.html segítségével érhető el a dokumentáció a generálást követően.

# Futtatás
Egy egyszerű parancs kiadásával fordítható és futtatható a projekt:
```
  ant compile jar run
```
