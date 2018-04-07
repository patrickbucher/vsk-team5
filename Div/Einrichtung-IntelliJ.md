---
title: Einrichten der Entwicklungsumgebung IntelliJ
subtitle: Version 1.0.0
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

Da es während des Projektes immer wieder zu Problemen mit der Entwicklungsumgebung IntelliJ kam, wurde diese Kurzanleitung verfasst, womit sämtliche Projektdaten und Maven-Artefakte zunächst komplett entfernt und später erneut eingerichtet wurden.

Die Anleitung wurde für macOS geschrieben und auch auf Linux getestet. Unter Windows muss die Pfadangabe `~` durch das jeweilige Benutzerprofilverzeichnis (Umgebungsvariable `%USERPROFILE%`) und die Umgebungsvariable `$TMPDIR` durch `%TEMP%` ersetzt werden.

# Löschen der Artefakte und Projekte
-   `~/.m2/repository` löschen
-   alle Ordner in `~/IdeaProjects` löschen

# Aufsetzen der Projektstruktur 
-   alle drei Projekte nach `~/IdeaProjects/` klonen
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-stringpersistor.git`
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-logger.git`
    -   `git clone git@gitlab.enterpriselab.ch:vsk-18fs01/g05-game.git`
-   IntelliJ starten
-   Neues Java-Projekt (mit Defaulteinstellungen) namens `vsk` erstellen
-   Alle drei VSK-Module importieren über Menü "File -\> New -\> Module
    from Existing Sources..."
    -   Reihenfolge: `stringpersistor`, `logger`, `game`
    -   Import module from external model: Maven
    -   Alle anderen Einstellungen belassen, "Next", "Finish"
    -   Bei Nachfrage "Maven projects need to be imported" (Fenster unten rechts) mit "Enable Auto-Import" antworten
-   Cmd-Shift-A: "Project Structure"
    -   Unter "Project Settings -\> Modules" sicherstellen, dass das "Language Level" auf 8 steht
-   IntelliJ schliessen

# Build mit Maven

-   Alle Module builden
    -   `cd ~/IdeaProjects/g05-stringpersistor && mvn package`
    -   `cd ~/IdeaProjects/g05-logger && mvn package`
    -   `cd ~/IdeaProjects/g05-game && mvn package`

# Testen der Umgebung

-   `config.xml` aus `g05-game` nach `vsk` kopieren
    -   Pfad von `<jarFile>` anpassen
-   IntelliJ öffnen
    -   `ConcurrentLoggerServer` starten
    -   `DemoLoggerClient` starten
        -   sicherstellen, dass `ch.hslu.vsk18fs.g05.loggercomponent.LoggerComponent` auf die Konsole ausgegeben wird (ansonsten stimmt etwas mit `config.xml` nicht)
    -   `cd $TMPDIR`
    -   `tail -f *.log` [^notepad]
        -   Die Log-Meldungen müssten nun erscheinen

[^notepad]: Dies entspricht auf Windows wohl dem Öffnen mehrerer Instanzen von `notepad.exe`
