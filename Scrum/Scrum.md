---
title: Scrum-Dokumente
subtitle: Version 1.0.0
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# Scrumdokumente:

## Stories (nach Epics)

### Epic Log-Ereignisse

1. Aufzeichnung Spielverlaufs: Als *Anwender* möchte ich *persistent festgehaltene Spielereignisse*, um *den Spielverlauf bei Bedarf nachvollziehen zu können*.
2. Aufzeichnung von Problemen: Als *Anwender* möchte ich *persistent festgehaltene Fehlermeldungen*, um *auftretende Probleme nachweisen zu können*.
3. Aufzeichnung von Exceptions: Als *Entwickler* möchte ich *persistent festgehaltene StackTraces*, um *geworfene Exceptions nachvollziehen zu können*.

### Epic Log-Level

4. Message-Level: Als *Entwickler* möchte ich *zu jeder Log-Meldung ein Message-Level setzen können*, um *Log-Meldungen auf verschiedenen Levels definieren zu können*.
5. Codiertes Filter-Level: Als *Entwickler* möchte ich *zur Laufzeit einen Filter-Level setzen können*, um *die Ausgabe der Log-Meldungen steuern zu können*.
6. Konfiguriertes Filter-Level: Als *Administrator* möchte ich *vor der Ausführung einen Filter-Level konfigurieren können*, um *die Ausgabe der Log-Meldungen steuern zu können*.

### Epic Log-Interface

7. Logger-Interface: Als *Entwickler* möchte ich *ein definiertes Logger-Interface zur Verfügung haben*, um *eine Logger-Komponente umsetzen zu können*.
8. Logger: Als *Entwickler* möchte ich *einen `Logger` zur Verfügung haben*, um *Log-Meldungen aus dem Game ausgeben zu können*.
9. Logger-Setup: Als *Entwickler* möchte ich *ein `LoggerSetup` zur Verfügung haben*, um *Zugriff auf eine `Logger`-Instanz zu bekommen*.

### Epic Architektur

10. Komponenten-Austausch: Als *Administrator* möchte ich *eine austauschbare und plattformunabhängige Logger-Komponente*, um *die Logger-Komponente zur Laufzeit ohne Code-Anpassungen und Neukompilation austauschen zu können*.
11. StringPersistor: Als *Entwickler* möchte ich *eine `StringPersistor`-Implementierung für Textdateien (`StringPersistorFile`) zur Verfügung haben*, um *geloggte Meldungen persistent in Textdateien festhalten zu können*.
12. Mehrere Log-Clients: Als *Administrator* möchte ich *einen Logger-Server, auf den mehrere Logger-Komponenten parallel loggen können*, um *einen Server für mehrere Clients zur Verfügung stellen zu können*.
13. Payload-Adapter: Als *Entwickler* möchte ich *einen Adapter für die Übergabe des Payloads*, um *Daten in strukturierter Form dem `StringPersistor` übergeben zu können*.
14. Adapter-Tests: Als *Entwickler* möchte ich *Unittests für den Adapter haben*, um *zu sehen, ob Änderungen am Adapter zu Problemen führen*.
15. Speicherformat-Strategien: Als *Entwickler* möchte ich *über leicht austauschbare Log-Strategien verfügen*, um *das Speicherformat für Textdateien einfach auf Code-Ebene wählen zu können*.

### Epic Persistenz

16. Log-Aufzeichnung: Als *Administrator* möchte ich *eine verlässliche Aufzeichnung der Log-Ereignisse auf dem Server*, um *die Ereignisse aus dem Spiel in korrekter Reihenfolge nachverfolgen zu können*.
17. Unterscheidung Log-Clients: Als *Administrator* möchte ich *nach Client-Instanz unterscheidbare Logdateien*, um *die Log-Meldungen verschiedener Clients auseinanderhalten zu können*. 
18. Textdatei-Log: Als *Administrator* möchte ich *Log-Ereignisse in einfachen Textdateien festhalten*, um *diese mit gängigen Werkzeugen betrachten und auswerten zu können (`grep`, `tail`, etc.)*.

### Epic Format

19. Speicherung Log-Quelle: Als *Administrator* möchte ich *die Quelle einer Logmeldung sehen*, um *verschiedene Clients und Sessions voneinander unterscheiden zu können*.
20. Speicherung Log-Level: Als *Administrator* möchte ich *den Level einer Logmeldung sehen*, um *diese je nach Bedarf nachträglich filtern zu können*.
21. Speicherung Ereignis-Zeitstempel: Als *Administrator* möchte ich *den Zeitstempel der Message-Erstellung* sehen, um *den genauen Zeitpunkt des Ereignisses zu kennen*.
22. Speicherung Log-Zeitstempel: Als *Administrator* möchte ich *den Zeitstempel des Message-Eingangs auf dem Server* sehen, um *Verzögerungen beim Logging-Vorgang erkennen zu können*.
23. Speicherung Message-Text: Als *Administrator* möchte ich *den Text der Log-Message auf dem Server sehen*, um *das aufgetretene Ereignis erkennen zu können*.

### Epic Konnektivität

24. Konfiguration Serverzugriff: Als *Administrator* möchte ich *die Erreichbarkeit des Servers mittels Konfigurationsdatei definieren können*, um *die Verbindung zum Log-Server ohne Quellcodeanpassung definieren zu können*.
25. Robuste Netzwerkanwendung: Als *Anwender* möchte ich *eine Anwendung mit robuster Netzwerkkonnektivität*, um *auch bei Netzwerkunterbrüchen einen ununterbrochenen Spielfluss ohne verlorene Log-Meldungen zu haben*.
26. Message-Queue: Als *Entwickler* möchte ich *auftretende Log-Ereignisse in einer Message-Queue zwischenspeichern können*, um *Log-Ereignisse bei einem Verbindungsunterbruch erneut senden zu können*.

### Epic Viewer

27. Viewer: Als *Anwender* möchte ich *einen Viewer für Logmeldungen haben*, um *Logmeldungen ohne Kenntnis des physischen Speicherorts auf dem Server betrachten zu können*.

### Rollen

- Ein *Entwickler* hat Zugriff auf den Quellcode und verfügt über die Fähigkeiten und Berechtigungen um Änderungen daran vorzunehmen.
- Ein *Administrator* hat Zugang auf beide konzeptionell involvierten Systeme (Client und Server) und Zugriff auf die Konfigurationsdateien aller involvierter Komponenten.
- Ein *Anwender* kann das Spiel auf dem Client ausführen, kann aber keine Änderungen an Quellcode und Konfigurationsdateien vornehmen und nicht auf den Server zugreifen.

## Definition of Done

- Funktionalität vom Product Owner abgenommen.
- Code-Review von mindestens einem Teammitglied durchgeführt.
- Bestehende Tests erfolgreich durchgelaufen.
- Eingeführte Features sind in der Projektdokumentation vermerkt.
- Änderungen in GitLab eingecheckt.
- Build auf Jenkins funktioniert.

## Sprintreviews

### Protokoll Review - Sprint 1

| Nr. / ID | Titel / Kurzbeschreibung | Version  | Status   |
| -------: | ------------------------ | -------- | -------- |
| 1        | Projektmanagementplan    | 1.0.0    | erledigt |
| 2        | Projektstrukturplan      | 1.0.0    | erledigt |
| 3        | Rahmenplan               | 1.0.0    | erledigt |
| 4        | Risikoanalyse            | 1.0.0    | erledigt |
| 5        | Scrum-Stories            | 1.0.0    | erledigt |

Im ersten Sprint konnten wir nicht alle Stories, welche ursprünglich für den ersten Sprint geplant waren, umsetzen. <br>

Dies liegt daran, dass wir sehr viel Zeit für die Einarbeitung in den Stoff, die Gruppenorganisation und -kommunikation gebraucht haben. Weiterhin benötigte auch das Aufsetzen der Umgebung mitsamt allen Tools eine gewisse Zeit.

**Im ersten Sprint konnten wir also folgende Artefakte bereitstellen: Projektmanagementplan, Projektstrukturplan, Rahmenplan, Risikoanalyse, Scrum-Stories.**

### Protokoll Review - Sprint 2

### Protokoll Review - Sprint 3

### Protokoll Review - Sprint 4

### Testprotokoll

Im Sprint 1 wurden noch keine Tests vorgenommen.

## Meilensteinberichte

### Meilensteinbericht 1

#### Zeitpunkt Meilenstein 1: Beginn SW04

#### Beschreibung Meilenstein 1

Der erste Meilenstein wurde erreicht, nachdem das Projekt bekannt gegeben wurde, die Gruppen gebildet wurden, alle nötigen Informationen abgegeben wurden, die Gruppen sich mit der Aufgabenstellung auseinandergesetzt haben und die Gruppen die nötige Entwicklungsumgebung und alle wichtigen Werkzeuge aufgesetzt haben.

Leider haben die Arbeiten des Meilenstein 1 ein wenig mehr Zeit als geplant in Anspruch genommen.

#### Meilensteinziele:
1. Projektauftrag als Gruppe entgegennehmen
2. Gruppe setzt sich mit Projektauftrag auseinander
3. Jedes Gruppenmitglied setzt die Entwicklungsumgebung und alle nötigen Werkzeuge auf

#### Wurden die Meilensteinziele erreicht?
1. Ja. Die Gruppenmitglieder waren alle jederzeit anwesend und konnten den Projektauftrag erfolgreich entgegennehmen.
2. Ja. Die Gruppe hat sich kennengelernt und erste Aufgaben wurden verteilt. Jedes Teammitglied hat sich selbständig mit der Thematik auseinandergesetzt.
3. Ja. Jedes Teammitglied hat die Entwicklungsumgebung und die Tools aufgesetzt.

### Meilensteinbericht 2

### Meilensteinbericht 3