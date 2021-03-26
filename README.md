# RoboRally Team 3 
### Team3: 
- Mathias Vehus
- Lars Holter 
- Jørgen Hersdal 
- Bård Løne
- André Bjørgum

## Shortcuts  
- [User Stories](https://github.com/MVehus/Team3/blob/main/Deliverables/Spesifikasjoner.md)
- [Meetings](https://github.com/MVehus/Team3/blob/main/Deliverables/M%C3%B8teReferat.md)
- [Manual Tests](https://github.com/MVehus/Team3/blob/main/Deliverables/ManuelleTester.md)
- Exercises
    - [Obligatorisk oppgave 1](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave1.md)
    - [Obligatorisk oppgave 2](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave2.md)
    - [Obligatorisk oppgave 3](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave3.md)

## How to play 

Clone this repository to your local machine.
- Open project in preferred IDE. 
- Run > Main.java
  - Create game and let people connect to your server
  - Join an existing game 

*If you want to play in fullscreen, go to preferences in Main Menu and select fullscreen.*

## Testing 

We have implemented automatic JUNIT tests for Player, Server and CardDeck. 

We have written Manual tests for Board and Game.

-> See *File Structure*

## File structure 
- Deliverables:
    - Meeting documentation
    - .md files for mandatory excerices
    - User stories, acceptance criteria and tasks. 
    - Manual tests 
  
- src 
    - Assets 
        - Skin (used for GUI)
        - Different images used to visualize game. 
    - Main folder with all Java code. 
    - A testing folder for JUNIT tests. 
    
## Known bugs
- Resizing Game window may lead to poor quality among the tiles.
- In some cases, on MacBooks, GameBoard and PlayerBoard won't fit players monitor. 
- If NullPointerException on "loadTextures(players)" just change the TimeDelay in Game.Create()


## Thanks to
- Arild from "Taco-laget" for the program card images.
- Czyzby for Skins. You can find his LibGDX skins on: [GitHub](https://github.com/czyzby/gdx-skins)