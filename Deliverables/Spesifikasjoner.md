# Spesifikasjoner for projektet

## Målet for applikasjonen
Målet for utviklingen av applikasjonen dette semesteret kan deles i to seksjoner, samarbeid og produkt.

En del av målet er å ende opp med en fungerende digital versjon av brettspillet RoboRally med multiplayer kapabilitet. Implementert med
funksjonaliteten spesifisert i MVP kravene, og gjerne mer om det lar seg gjøre. 

En annen gjerne viktigere del av målet er å forbedre samarbeidsevnene til alle teammedlemmene, samt lære seg å mestre vektøyene som brukes 
i utvikling drevet av et team med personer. Et mål for vår utvikling av denne applikasjonen må være å skape et trygt og effektivt 
utviklermiljø hvor alle parter føler seg innvolvert og alle bidrar for å levere best mulig sluttprodukt. Dette målet vil vi prøve å
strekke oss mot ved å yte vårt beste individuelt, og utnytte eksisterende retningslinjer for systemutvikling i en teamsetting.

# Brukerhistorier

| Nummer       | Beskrivelse         | Kategori    | Status                  |
|--------------|---------------------|-------------|-------------------------|
|[ 1](#gui1)   | Vise kart           | Gui         |<ul><li>- [x] </li></ul> |
|[ 2](#gui2)   | Vise spillebrikker  | Gui         |<ul><li>- [x] </li></ul> |
|[ 3](#gui3)   | Input flytter brikke| Gui         |<ul><li>- [x] </li></ul> |
|[ 4](#gui4)   | Flaggindikasjon     | Gui         |<ul><li>- [x] </li></ul> |
|[ 5](#gui5)   | Window/Fullscreen   | Gui         |<ul><li>- [x] </li></ul> |
|[ 6](#gui6)   | Lagre preferanser   | Gui         |<ul><li>- [x] </li></ul> |
|[ 7](#gui7)   | Vise skadetokens    | Gui         |<ul><li>- [ ] </li></ul> |
|[ 8](#gui8)   | Kjøre runde         | Gui         |<ul><li>- [ ] </li></ul> |
|[ 9](#gui9)   | Programkort         | Gui         |<ul><li>- [ ] </li></ul> |
|[10](#gui10)  | Power down          | Gui         |<ul><li>- [ ] </li></ul> |
|[11](#gui11)  | Kjøre programkort   | Gui         |<ul><li>- [ ] </li></ul> |
|[12](#gui12)  | Laser               | Gui         |<ul><li>- [ ] </li></ul> |
|[ 1](#logic1) | Flaggindikasjon     | Logic       |<ul><li>- [ ] </li></ul> |
|[ 2](#logic2) | Win-condition       | Logic       |<ul><li>- [ ] </li></ul> |
|[ 3](#logic3) | Utdeling av kort    | Logic       |<ul><li>- [ ] </li></ul> |
|[ 4](#logic4) | Velge kort          | Logic       |<ul><li>- [ ] </li></ul> |
|[ 5](#logic5) | Skadekonsekvens     | Logic       |<ul><li>- [ ] </li></ul> |
|[ 6](#logic6) | Kjøre runde         | Logic       |<ul><li>- [ ] </li></ul> |
|[ 7](#logic7) | Stokke kortstokk    | Logic       |<ul><li>- [ ] </li></ul> |
|[ 8](#logic8) | Programkort         | Logic       |<ul><li>- [ ] </li></ul> |
|[ 9](#logic9) | Timer               | Logic       |<ul><li>- [ ] </li></ul> |
|[10](#logic10)| Power down          | Logic       |<ul><li>- [ ] </li></ul> |
|[11](#logic11)| Kjøre programkort   | Logic       |<ul><li>- [ ] </li></ul> |
|[12](#logic12)| Dytting             | Logic       |<ul><li>- [ ] </li></ul> |
|[13](#logic13)| Spesialtfelt        | Logic       |<ul><li>- [ ] </li></ul> |
|[14](#logic14)| Laserskade          | Logic       |<ul><li>- [ ] </li></ul> |
|[ 1](#multi1) | Spille med andre    | Multiplayer |<ul><li>- [ ] </li></ul> |
|[ 2](#multi2) | Host/client valg    | Multiplayer |<ul><li>- [x] </li></ul> |
|[ 3](#multi1) | Lobby               | Multiplayer |<ul><li>- [ ] </li></ul> |


## Gui

<a name="gui1"></a>
### 1: 
#### Brukerhistorie
Bruker skal kunne kjøre applikasjonen og spillebrettet skal vise seg på skjermen i en egen applikasjon.
#### Akseptansekrav
- Kartet må vises når applikasjonen kjøres.
#### Arbeidsoppgaver
- Lage en kjørbar applikasjon.
- Spillbrettet vises.
#### Krav brukerhistorien oppfyller

<a name="gui2"></a>
### 2:
#### Brukerhistorie
Når bruker skal spille, skal det kunne vises spillebrikker på brettet.
#### Akseptansekrav
- Kartet som vises når applikasjonen kjøres skal kunne populeres med spillebrikker.
#### Arbeidsoppgaver
- Legge inn spillbrikke til brukeren når spilbrettet vises.
#### Krav brukerhistorien oppfyller

<a name="gui3"></a>
### 3:
#### Brukerhistorie
Når bruker gir input (tastetrykk el) skal roboten flytte på seg.
#### Akseptansekrav
- Roboten flytter seg ment retning som tilsvarer input fra bruker.
#### Arbeidsoppgaver
- Få til at bruker kan komme med input.
- Flytte brikken til bruker etter gitt input.
#### Krav brukerhistorien oppfyller


<a name="gui4"></a>
### 4: 
#### Brukerhistorie
Når roboten befinner seg på et flagg, skal dette indikeres til bruker og spillogikken skal registrere det.
#### Akseptansekrav
- Indre logikk registrer at robot har vært på/innom et flagg.
- Bruker får bekreftet at hun/han har vært innom et flagg.
#### Arbeidsoppgaver
- Registrere at en robot har nådd fram til et flagg.
- Gi bruker en indikasjon når roboten har nådd et flagg.
#### Krav brukerhistorien oppfyller

<a name="gui5"></a>
### 5:
#### Brukerhistorie
Som bruker ønsker jeg å velge mellom fullscreen- eller vindumodus før jeg starter spillet.
#### Akseptansekrav
- GUI som viser en avkrysningsboks for fullscreen.
#### Arbeidsoppgaver
- Implementere et vindu som viser spillerens preferanser.
- Vise en avkrysningsboks som brukeren kan klikke på.
#### Krav brukerhistorien oppfyller

<a name="gui6"></a>
### 6:
#### Brukerhistorie
Når jeg spiller ønsker jeg å lagre preferansene mine til senere.
#### Akseptansekrav
- Preferansene til brukeren lagres i en fil lokalt på maskinen.
#### Arbeidsoppgaver

#### Krav brukerhistorien oppfyller

<a name="gui7"></a>
### 7:
#### Brukerhistorie
Når roboten min tar skade vil jeg at det skal vise.
#### Akseptansekrav
- Vise antall skadetokens spilleren har
#### Arbeidsoppgaver
#### Krav brukerhistorien oppfyller

<a name="gui8"></a>
### 8:
#### Brukerhistorie
Når en tur starter så skal programmet gå gjennom alle stegene i turen.
#### Akseptansekrav
Når en tur kjører gjennom alle stegene som en tur skal ha.
#### Arbeidsoppgaver
- Spillere kan arrangere programkort
- Spiller kan melde om "power down"
#### Krav brukerhistorien oppfyller

<a name="gui9"></a>
### 9:
#### Brukerhistorie
Når alle utenom ein bruker har valgt kort og trykt på "ferdig" knapp så har
den siste brukeren 30 sekunder på å velge kort før det blir tildelt tilfeldig.
#### Akseptansekrav
At gamelogic starter timer på rett tidspunkt, og at reglene følges
i forhold til programmeringsstadiet.
#### Arbeidsoppgaver
- Legge til ferdigknapp
#### Krav brukerhistorien oppfyller

<a name="gui10"></a>
### 10:
#### Brukerhistorie
Som spiller vil eg kunne annonsere "power down"
#### Akseptansekrav
Når spilleren har mulighet til å bruke "power down" og alle konsekvenser blir følgt
#### Arbeidsoppgaver
- Lage power down knapp
- Indikere at spiller er i power down modus
- Hvis robot blir destruert før "power down" starter, gi bruker valg om å
  komme tilbake i "power down" modus eller ikke
- Hvis bruker var i "power down" forige tur, gi bruker valg om å
  forsette "power down" før kort blir delt ut.
#### Krav brukerhistorien oppfyller

<a name="gui11"></a>
### 11:
#### Brukerhistorie
Spillet skal kjøre programkortene til spillerene
#### Akseptansekrav
Når spillet kjører alle programkortene til alle spillere i rett rekkefølge.
#### Arbeidsoppgaver
- Vise alle programkort for alle spillere for en slot om gangen.
#### Krav brukerhistorien oppfyller

<a name="gui12"></a>
### 12:
#### Brukerhistorie
Roboten min skal ta skade hvis den blir truffet av laser
#### Akseptansekrav
At roboter tar skade av lasere når laserene skyter.
#### Arbeidsoppgaver
- Vise at robotene sin laser skyter
#### Krav brukerhistorien oppfyller


## Gamelogic

<a name="logic1"></a>
### 1:
#### Brukerhistorie
Når roboten befinner seg på et flagg, skal dette indikeres til bruker og spillogikken skal registrere det.
#### Akseptansekrav
- Indre logikk registrer at robot har vært på/innom et flagg.
- Bruker får bekreftet at hun/han har vært innom et flagg.
#### Arbeidsoppgaver
- Registrere at en robot har nådd fram til et flagg.
- Gi bruker en indikasjon når roboten har nådd et flagg.
#### Krav brukerhistorien oppfyller

<a name="logic2"></a>
### 2:
#### Brukerhistorie
Spiller vinner dersom spillets krav for å vinne blir oppfylt.
#### Akseptansekrav
- At når spillerens robot vært innom alle flagg i riktig rekkefølge, resulterer det i at spilleren vinner.
#### Arbeidsoppgaver
- Legge til at roboten registrerer at det er det rette flagget den har nådd.
- Registrere at roboten har vert innom alle flagg.
- Gi en melding at spilleren som har vert innom alle flagg først vinner.
#### Krav brukerhistorien oppfyller

<a name="logic3"></a>
### 3:
#### Brukerhistorie
Når jeg spiller ønsker jeg å få tildelt nye kort hver runde
#### Akseptansekrav
- Spilleren får tildelt riktig mengde med kort
- Etter runden kvitter spilleren seg med brukte kort.
#### Arbeidsoppgaver
- Opprette kortstokk
- Opprette Kort klassen som holder på type og prioritet.
#### Krav brukerhistorien oppfyller

<a name="logic4"></a>
### 4:
#### Brukerhistorie
Som bruker ønsker jeg å velge mellom fem kort og kvitte meg med resten.
#### Akseptansekrav
- Spilleren har mulighet til å velge fem kort
#### Arbeidsoppgaver
- Implementere metode i Player klassen
-
#### Krav brukerhistorien oppfyller

<a name="logic5"></a>
### 5:
#### Brukerhistorie
Skade roboten min tar skal gi meg konsekvenser.
#### Akseptansekrav
- Begrense brukerens tilgjengelighet på kort og programmeringsslots i forhold til skade
#### Arbeidsoppgaver
- Dele ut kort i forhold til skade.
- Låse programmeringsslots i forhold til skade.
#### Krav brukerhistorien oppfyller

<a name="logic6"></a>
### 6:
#### Brukerhistorie
Når en tur starter så skal programmet gå gjennom alle stegene i turen.
#### Akseptansekrav
Når en tur kjører gjennom alle stegene som en tur skal ha.
#### Arbeidsoppgaver
- Dele ut kort
- Spillere kan arrangere programkort
- Spiller kan melde om "power down"
- Kjør programkort i rekkefølge
- Kjør "end of turn" effekter
#### Krav brukerhistorien oppfyller

<a name="logic7"></a>
### 7:
#### Brukerhistorie
Når programmet skal dele ut kort, skal kortstokken stokkes.
#### Akseptansekrav
At kortstokken er i en tilfeldig rekkefølge før det deles ut kort.
#### Arbeidsoppgaver
- Stokke kortstokken.
#### Krav brukerhistorien oppfyller

<a name="logic8"></a>
### 8:
#### Brukerhistorie
Når eg har fått utdelt kort vil eg kunne arrangere kortene i programkortet.
#### Akseptansekrav
At brukeren kan velge kva kort skal i kva posisjon i programkortet.
#### Arbeidsoppgaver
- Bruker kan velge kort til en ledig slot.
- Bruker kan ta vekk kort fra ein slot som har kort.
- Ikke la bruker få bruke programslots hvis bruker har skade som
  tilsier at dei er låst
#### Krav brukerhistorien oppfyller

<a name="logic9"></a>
### 9:
#### Brukerhistorie
Når alle utenom ein bruker har valgt kort og trykt på "ferdig" knapp så har
den siste brukeren 30 sekunder på å velge kort før det blir tildelt tilfeldig.
#### Akseptansekrav
At gamelogic starter timer på rett tidspunkt, og at reglene følges
i forhold til programmeringsstadiet.
#### Arbeidsoppgaver
- Bruker kan melde fra om at programkortet er ferdig.
- Starte timer når det er en spiller som ikke er ferdig.
- Tildele kort tilfeldig hvis bruker ikke blir ferdig innen tidsfrist.
#### Krav brukerhistorien oppfyller

<a name="logic10"></a>
### 10:
#### Brukerhistorie
Som spiller vil eg kunne annonsere "power down"
#### Akseptansekrav
Når spilleren har mulighet til å bruke "power down" og alle konsekvenser blir følgt
#### Arbeidsoppgaver
- Sette opp power down funksjonalitet
- Ikke dele ut kort til spiller som starter "power down"
- Reparere robot når "power down" starter
- Hvis robot blir destruert før "power down" starter, gi bruker valg om å
  komme tilbake i "power down" modus eller ikke
- Hvis bruker var i "power down" forige tur, gi bruker valg om å
  forsette "power down" før kort blir delt ut.
#### Krav brukerhistorien oppfyller

<a name="logic11"></a>
### 11:
#### Brukerhistorie
Spillet skal kjøre programkortene til spillerene
#### Akseptansekrav
Når spillet kjører alle programkortene til alle spillere i rett rekkefølge.
#### Arbeidsoppgaver
- Flytte roboter i rett rekkefølge i forhold til prioritet.
- Elementer på brettet tar sin tur etter hver runde med programkort.
#### Krav brukerhistorien oppfyller

<a name="logic12"></a>
### 12:
#### Brukerhistorie
Hvis roboten min går på et felt med en robot, så vil eg dytte den vekk.
#### Akseptansekrav
At roboter dytter vekk andre roboter i samme retning hvis det er mulig.
#### Arbeidsoppgaver
- Sjekke om robot står på et felt som en robot flytter til.
- Sjekke om feltet ved siden av er ledig.
- Stoppe roboten om dytting ikke er mulig.
#### Krav brukerhistorien oppfyller

<a name="logic13"></a>
### 13:
#### Brukerhistorie
Hvis roboten min står på/går over et felt med spesielle attributter så skjer det noe.
#### Akseptansekrav
At spesialfelt sine oppgaver blir utført på roboter.
#### Arbeidsoppgaver
- Sjekk at rullefelt flytter roboter.
- Sjekk at rullefelt med sving roterer roboten hvis et rullefelt flytta roboten inn på feltet.
- Sjekk at roboter ikke dytter på rullefelt.
- Sjekk at roboter dør hvis dei går over hull.
- Sjekk om robot står på reperasjonsfelt og reparer hvis roboten har
  skade når en programrunde er ferdig.
- Sjekk om robot står på flagg, og om det er det neste flagget roboten skal nå.
- Sjekk at roboten dør hvis den er utenfor brettet.
#### Krav brukerhistorien oppfyller

<a name="logic14"></a>
### 14:
#### Brukerhistorie
Roboten min skal ta skade hvis den blir truffet av laser
#### Akseptansekrav
At roboter tar skade av lasere når laserene skyter.
#### Arbeidsoppgaver
- Skyte robotene sin laser
- Gi roboter skade når de blir truffet av laser.
- Ikke gi skade til robot hvis en robot blokkerer laser.
#### Krav brukerhistorien oppfyller


## Multiplayer

<a name="multi1"></a>
### 1:
#### Brukerhistorie
Som bruker vil jeg kunne spille med andre spillere på forskjellige maskiner
#### Akseptansekrav
- Fungerende multiplayer funksjonalitet
- Korrekt oppdatering av gamesate på alle tilkoblede maskiner
#### Arbeidsoppgaver
- Lese seg opp på relevant kunnskap om klient/server
- Utvikle nødvendige klasser for mulitplayer funksjonalitet.
#### Krav brukerhistorien oppfyller

<a name="multi2"></a>
### 2:
#### Brukerhistorie
Når jeg starter spillet, vil jeg ha mulighet til å velge mellom å bli med i et
eksisterende spill, eller starte et nytt.
#### Akseptansekrav
- Implementert start meny funksjonalitet
  Fungerende lobbysystem
#### Arbeidsoppgaver
#### Krav brukerhistorien oppfyller

<a name="multi3"></a>
### 3:
#### Brukerhistorie
Når jeg befinner meg i en lobby vil jeg kunne se hvem som er med i spillet.
#### Akseptansekrav
- Fungerende lobbysystem
- Se hvem som er koblet sammen (hvem som er i min lobby)
- Kunne starte spillet
#### Arbeidsoppgaver
#### Krav brukerhistorien oppfyller




### :
#### Brukerhistorie
#### Akseptansekrav
#### Arbeidsoppgaver
#### Krav brukerhistorien oppfyller
