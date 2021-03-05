#Oblig 2 

##Deloppgave 1

###Hvordan fungerer rollene i teamet?
Vi føler at rollene i teamet har fungert på en bra måte, vi har strukturert det litt mer i forhold til at det har blitt flere arbeidsoppgaver, og vi har satt opp roller slik at det er mulig at noen har flere roller. Så den nye strukturen for denne innleveringen ser som følgene ut:
- Teamleader: Mathias
- GUI-ansvarlig: Lars
- Server-Client-Ansvarlig: Mathias, Bård, Jørgen
- Testansvarlige: Bård og André
- Koding: Alle
- Kundekontakt: Kenneth Langedal

Ellers har alle bidratt til dokumentasjon og deltatt i møter.

###Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.

For øyeblikket så trenger vi ikke flere, men det kan bli endringer til senere innleveringer.
Vi diskuterer bra i team-møtene om hvordan det går med oppgavene og fordeler arbeidsoppgaver til senere oppgaver.
De ulike rollene forklarer mest kva som er fokuset til den/de personen(e) for denne perioden.
Men slik det har sett ut nå så er dette noe som sannsynligvis blir stående gjennom prosjektet.

###Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?

Med tanke på prosjektmetodikk og erfaring i teamet, så  synes vi at vi har gjort gode valg, det er bra kommunikasjon og godt samarbeid.


###Hvordan er gruppedynamikken?

Gruppedynamikken har vært veldig bra til nå, alle har lyst til å jobbe sammen for et best mulig sluttresultat.
Vi jobber mye med oppgaven ved siden av gruppetimer.

###Hvordan fungerer kommunikasjonen for dere?

Alle i gruppen bidrar i møtene med sine meninger om hvordan utviklingen burde skje fremover.
Alle forslag blir hørt og diskutert sånn at beste mulig løsning kan oppnås.
Kommunikasjonen i gruppen funker bra.
Medlemmene stiller forberedt opp på møtene og forteller hva de har gjort og hvordan det går med arbeidsoppgavene.


###Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.

Ting en vil forberede:
- Bedre forståelse av kodebasen slik at en kan jobbe mer effektivt
- Klare arbeidsoppgaver
- Jevnligere oppdatering av Project board
- Sette prioriteringer på oppgaver som skal løses og bruke disse
Ting som gikk bra:
- God flyt i møtene, særs effektivt, gode diskusjoner
- Tilgjengelighet på Discord.
- Jobber jevnt og trutt med oppgavene før levering

###Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.

Forberedningspunkter:
- Bedre forståelse av kodebasen slik at en kan jobbe mer effektivt
- Jevnligere oppdatering av Project board
- Sette prioriteringer på oppgaver som skal løses og bruke disse


##Deloppgave 2


###Forklar kort hvordan dere har prioritert oppgavene fremover
Planen fremover er å fikse spillvinduet hvor vi ønsker å minimere kartet for å gjøre plass til et "PlayerBoard" ved siden som gir brukeren
mulighet til å velge kort og se statusen til spiller.
Dette gjør at vi videre kan begynne å implementere spilllogikken: Krav 9 hvor vi kan bevege robot utifra valgte kort.

###Har dere gjort justeringer på kravene som er med i MVP? Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge utfra hva som er gitt fra kunde, hvorfor er dette gjort?


####Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.
Vi har prioritert Server-Client implementasjonen da vi hovedsakelig tenkte å bruke P2P.

Siden forrige gang har vi opprettet GUI som støtter for flere vinduer og spillerens preferanser.
Vi har opprettet et Player-objekt som holder styr på spillerens liv, mulighet for å velge kort, damagetokens etc.
Vi har opprettet tester for Player- og CardDeck klassene.
Her tester vi at CardDeck inneholder riktige kort basert på deres prioritering og verdi.
I PlayerTest tester vi at spilleren får riktig mengde med kort basert på antall skade spilleren har tatt.

##Deloppgave 3

####Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.
- Se README.md

#### Klassediagram

Link til klassediagram [APP CREATELY](https://app.creately.com/diagram/E4uRJsPIcEz/edit)

#### MVP
MVP:
- Vise et spillebrett ✅
- Vise brikke på spillebrett ✅
- Flytte brikke (vha taster e.l. for testing) ✅
- Robot besøker flagg ✅
- Robot vinner ved å besøke flagg ✅
- Spille fra flere maskiner (vise brikker for alle spillere, flytte brikker for alle spillere) ✅
    - Fått til server- og clientoppsettet. Spillere kan bli med i et eksisterende spill ved å skrive inn PORT og IP- addresse.
- Dele ut kort
    - Ikke implementert dette i GUI, men opprettet tester for dette CardDeckTest. Player får tildelt riktig mengde kort basert på hvor mange damage tokens spiller har.
- Velge 5 kort
    - Dette er ikke implementert enda da vi ikke har fått lagt til dette i GUIen. Planen til neste innlevering er å lage et område ved siden av spillebrettet som legger støtte til at bruker kan velge kort, programmere runden,  se liv og se damagetokens.
- Bevege robot ut fra valgte kort
    - Kommer til neste innlevering.



