
# RoboRally Team 3 (Group 2)

### Team3:
- Mathias Vehus
- Lars Holter
- Jørgen Hersdal
- Bård Løne
- André Bjørgum

#### Shortcuts
- [User Stories](https://github.com/MVehus/Team3/blob/main/Deliverables/Spesifikasjoner.md)
- [Meetings](https://github.com/MVehus/Team3/tree/main/Deliverables/m%C3%B8tereferat)
- [Manual Tests](https://github.com/MVehus/Team3/blob/main/Deliverables/ManuelleTester.md)
- [UML DIAGRAM](https://app.creately.com/diagram/E4uRJsPIcEz/edit)
- Exercises
    - [Obligatorisk oppgave 1](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave1.md)
    - [Obligatorisk oppgave 2](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave2.md)
    - [Obligatorisk oppgave 3](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave3.md)
    - [Obligatorisk oppgave 4](https://github.com/MVehus/Team3/blob/main/Deliverables/ObligatoriskOppgave4.md)

## How to play

### STEP 1 : Download Files
Clone this repository to your local machine.
- Open project in preferred IDE (*IntelliJ, Eclipse etc*).

### STEP 2 : Run Main.java
1. Go into project, navigate to Main.java (*src/main/app/main.java*)
2. Run Main.java
    - In IntelliJ you can press the green play button ▶ (top right) or right click and select: Run Main.java
    - In Eclipse you can right click on Main.java and select Run file.



### STEP 3 : Setup Game or join game
#### (1) HOST GAME
- Create game and let people connect to your server
    - Requirements: Port Forwarding
        - You will then see your IP address and which port to use

#### (2) JOIN GAME
- Join an existing game
    - Type in IP address and port (*which you get from the host*), look in Terminal for connection status.

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
- DrawLasers work, but we have not yet implemented functionality that removes laserCells. We have drawLaser()- method in Game, but commented it out.
- Push don't work when using BackUp- card.
-

## Thanks to
- Arild from "Taco-laget" for the program card images.
- Czyzby for Skins. You can find his LibGDX skins on: [GitHub](https://github.com/czyzby/gdx-skins)
