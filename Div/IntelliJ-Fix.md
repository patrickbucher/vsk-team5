---
author: Patrick Bucher
title: 'IntelliJ-Fix'
---
-   `~/.m2/repository` löschen
-   alle Ordner in `~/IdeaProjects` löschen
-   alle drei Projekte nach `~/IdeaProjects/` klonen
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-stringpersistor.git`
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-logger.git`
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-game.git`
-   IntelliJ starten
-   Neues Java-Projekt (mit Defaulteinstellungen) namens `vsk` erstellen
-   Alle drei VSK-Module importieren über Menü "File -\> New -\> Module
    from Existing Sources..."
    -   Import module from external model: Maven
    -   Alle anderen Einstellungen belassen, "Next", "Finish"
-   Cmd-Shift-A: "Project Structure"
    -   Unter "Modules" sicherstellen, dass das "Language Level" überall
        auf 8 steht
-   IntelliJ schliessen
-   Alle Module builden
    -   `cd ~/IdeaProjects/g05-stringpersistor && mvn package`
    -   `cd ~/IdeaProjects/g05-logger && mvn package`
    -   `cd ~/IdeaProjects/g05-game && mvn package`
-   `config.xml` aus `g05-game` nach `vsk` kopieren
-   IntelliJ öffnen
    -   `ConcurrentLoggerServer` starten
    -   `DemoLoggerClient` starten
    -   `cd $TMPDIR`
    -   `tail -f *.log`
