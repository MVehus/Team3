# Manuelle Tester

## Tester i Create Game
Spilleren skal kunne lage et nytt spill
##### Test steg:
1. Kjør programmet
2. Trykk på "Create Game"-knappen i hovedmenyen.
3. I "Port"-feltet skriv inn port number
4. Trykk på "Create Server"-knappen
5. Trykk på "Create Game"-knappen
##### Forventet resultat:
Spilleren skal få opp et nytt spillbrett


## Tester i Join Game
Spilleren skal kunne bli med i et spill
##### Forutsetning: 
Spilleren må ha en host han/hun kan koble seg til.
##### Test steg:
1. Kjør programmet
2. Trykk på "Join Game"-knappen i hovedmenyen.
3. Skriv inn IP og Port i henholdsvis "IP"- og "Port"-feltet
4. Trykk på "Connect"-knappen
##### Forventet resultat:
Spilleren skal bli med i et eksisterende spill.

## Få spillet over i Fullscreen
Spilleren skal kunne velge å spille spillet i Fullscreen-mode fra "Preferences"
##### Forutsetning:
Spilleren må være i window-mode (dette er default når man kjører programmet).
##### Test steg:
1. Kjør programmet
2. Trykk på "Preferences"-knappen i hovedmenyen.
3. Trykk på checkboxen ved "Fullscreen"
##### Forventet resultat:
Spillet skal nå være i Fullscreen-mode.


## Lese regler og returnere til hovedmenyen
Spilleren skal kunne lese reglene og deretter returnere til hovedmenyen.
##### Test steg:
1. Kjør programmet
2. Trykk på "Rules"-knappen i hovedmenyen
3. Trykk på "Main menu"-knappen inne på "Rules"-siden
##### Forventet resultat:
Spilleren skal få et bilde av spillets regler i "Rules" og deretter returnere til hovedmenyen.


## Avslutte programmet
Spilleren skal kunne avslutte programmet fra hovedmenyen.
##### Forutsetning:
Spilleren må være på spillets hovedmeny
##### Test steg:
1. Kjør programmet
2. Trykk på Exit-knappen i hovedmenyen
##### Forventet resultat:
Programmet skal avsluttes.


## Kan skru musikk av/på
Spilleren skal kunne skru av og på musikk fra preferences.
##### Forutsetning:
Musikk på være enten på eller av
##### Test steg:
1. Kjør programmet
2. Trykk på "Preferences"-knappen i hovedmenyen
3. Trykk på checkboxen ved "Music"
##### Forventet resultat:
Dersom musikk var av når programmet ble kjørt skal den nå være på, dersom den var på skal den nå være av.


## Kan endre volum på musikk
Spilleren skal kunne endre volum på musikk.
##### Forutsetning:
Musikken må være på.
##### Test steg:
1. Kjør programmet
2. Trykk på "Preferences"-knappen i hovedmenyen
3. Endre på volumbaren til høyre for "Music volume", til enten høyere eller lavere lyd.
##### Forventet resultat:
Om du drar volumbaren mot høyre skal musikken være høyere, om du drar den mot venstre skal musikken bli lavere.


## Tester i Game

##### Forutsetning:
Alle spillere må være koblet til serveren og lagt til i List<Player> players; 

##### Test 1:
>>Game: boolean canMove(Player player)
##### Test steg:
1. Velg spiller som testobjekt
2. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om spilleren kan bevege seg, returnerer False om spilleren blir blokkert av en vegg. 

##### Test 2:
>>Game: boolean playerOnNextCell(Player player)
##### Test steg:
1. Velg spiller som testobjekt
2. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om det står en annen spiller på neste celle, false om ikke.

##### Test 3:
>>Game: List<Vector2> getNextCells(Player player, int distance)
##### Test steg:
1. Velg spiller som testobjekt og distance (antall celler)
2. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om metoden returnerer en liste med riktig antall celler (tar til rette for hjørner og ugyldige posisjoner).

##### Test 4:
>>Game: List<Player> getAllPlayersInLine(Player player)
##### Test steg:
1. Velg spiller som testobjekt
2. Kall metoden med spiller
3. itererer igjennom listen som returneres og sjekk om den inneholder riktige player- objekter. 
##### Forventet resultat:
Returnerer True om metoden returnerer en liste med riktig antall celler og riktige spillere.

##### Test 5:
>>Game: boolean canPush(Player player, List<Player> playersOnLine)
##### Test steg:
1. Velg spiller som testobjekt og getAllPlayersInLine- listen
2. Iterer igjennom alle naboer og sjekk om den siste naboen kan bevege seg ut ifra player sin Direction. 
##### Forventet resultat:
Returnerer True om siste spiller kan bevege seg i riktig retning, ellers returnerer den false.

##### Test 6:
>>Game: Player getPlayerOnCell(Vector2 cell)
##### Test steg:
1. Velg celle du vil sjekke og opprett Vector2 posisjon med spiller.  
2. Iterer igjennom alle spillere og sjekker deres posisjon. 
3. Returnerer Spiller som står på posisjon, null om den er ledig. 
##### Forventet resultat:
Returnerer True om cellen er ledig og metoden returnerer null
Returnerer True om metoden returnerer korrekt spiller

##### Test 7.1:
>>Game: TiledMapTileLayer.Cell getPlayerTexture(Player player)
##### Test steg:
1. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om metoden returnerer WonCell når spiller har alle flagg. 

##### Test 7.2:
##### Test steg:
1. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om metoden returnerer DiedCell når spiller er død (isAlive = false). 

##### Test 7.3:
##### Test steg:
1. Kall metoden med spiller
##### Forventet resultat:
Returnerer True om metoden returnerer Cell når spiller verken har alle flagg eller er død. 

##### Test 8:
>>Game: push(Player player, List<Player> players)
##### Test steg:
1. Kall metoden med spiller og spillere han vil dytte på. 
2. Iterer igjennom posisjoner til spillere
##### Forventet resultat:
Returnerer True metoden endrer på alles posisjoner i henhold til Player.Direction 


## Tester i Board

##### Test 1:
>>Game: List<Tile> getTilesOnCell(float x, float y)
##### Test steg:
1. Velg en posisjon du vil returnere på Brettet (x,y) koordinater
2. Kall metoden
3. Iterer igjennom listen som returneres. 
##### Forventet resultat:
Returnerer true om listene er like, med samme størrelse - false ellers. 


##### Test 2:
>>Game: TiledMapTileLayer getLayer(Tile layer)
##### Test steg:
1. Velg et layer fra Tile- klassen (enum) du vil returnere
##### Forventet resultat:
Returnerer true om metoden returnerer riktig TiledMapTileLayer objekt fra hashmappet som blir initialisert. False ellers. 

##### Test 3:
>>Game: int getNumColumns()
##### Test steg:
1. Opprett Board Objekt med Map
2. Lagre størrelsen
##### Forventet resultat:
Returnerer true om størrelsen er riktig med det som returneres. 


##### Test 4:
>>Game: int getNumRows()
##### Test steg:
1. Opprett Board Objekt med Map
2. Lagre størrelsen
##### Forventet resultat:
Returnerer true om størrelsen er riktig med det som returneres.