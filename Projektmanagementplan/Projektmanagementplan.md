---
title: Projektmanagementplan
subtitle: Verteilte System und Komponenten
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
version: 1.0.0
---

# Projektorganisation

## Organisationsplan

![Organigramm](img/Organigramm.png)

## Rollen & Zuständigkeiten

### Rollen

- Projektleiter: Sascha Sägesser
- Interfacekomitee: Patrick Bucher
- Product Owner: Fabian Meyer
- Scrum Master: Pascal Kiser

### Zuständigkeit

- Dokumentation: Patrick Bucher
- Build: Pascal Kiser
- Testing: Fabian Meyer
- Codereview: Patrick Bucher
- Sprintreviews: Sascha Sägesser
- Meilensteinberichte: Fabian Meyer
- Risikomanagement: Pascal Kiser

# Projektführung

## Rahmenplan

Der Rahmenplan stellt eine Übersicht auf abstrakter Ebene dar. Die wichtigsten Elemente sind die definierten Meilensteine und Sprints. Dabei ist darauf zu achten, dass die Vorgaben der Aufgabenstellung, wie z.B. die Zwischen- und Schlussabgabe, von Anfang an in das Projekt miteingeplant werden.

![Rahmenplan](img/Rahmenplan.png)

## Meilensteinplan

Das komplette Projekt ist in vier Meilensteine aufgeteilt. Der erste Meilenstein entspricht dem Projektstart, also der ersten Vorlesung. Der zweite Meilenstein stellt den Start der Projektumsetzung dar und die beiden letzten Meilensteine ergeben sich aus der Zwischen- und Schlussabgabe. Zu jedem Meilenstein gibt es Deliverables:

1. Meilenstein (19.02.2018)
    - Auftrag "Message-Logger"
2. Meilenstein (12.03.2018)
    - ProductBacklog
    - Sprintplanung S1/S2
    - Detailplanung S1
    - Meilensteinbericht MS 2
3. Meilenstein (08.04.2018)
    - Release 1 gemäss Muss-Features deployed und lauffähig
    - Sprintplanung S3/S4
    - Meilensteinbericht MS 3
4. Meilenstein (13.05.2018)
    - Release 2 gemäss Muss-Features deployed und lauffähig
    - Meilensteinbericht MS 4

## Sprintplan

Die Projektdurchführung (MS 3 / 4) ist in vier Sprints aufgeteilt. Jeder Sprint dauert zwei Wochen. Die Detaillierte Sprintplanung ist mittels des Online-Tools "ScrumDo" realisiert.

| Sprint  | Termin     | Deliverables                        |
| ------: | ---------- | ----------------------------------- |
| 1       | 25.03.2018 | Sprintreview S1, Sprintplanung S2   |
| 2       | 08.04.2018 | Sprintreview S2, Sprintplanung S3   |
| 3       | 29.04.2018 | Sprintreview S3, Sprintplanung S4   |
| 4       | 13.05.2018 | Sprintreview S4                     |

## Projektstrukturplan

Um das Projekt besser planen, durchzuführen und kontrollieren zu können, ist dieses in mehrere Teilprojekte, welche wiederum in Teilaufgaben gegliedert sind, aufgeteilt.

![Projektstrukturplan](img/Projektstrukturplan.png)

## Risikomanagement
## Aufwandschätzung
## Ressourcenplan
## Projektkontrolle

# Projektunterstützung

## Tools für Entwicklung, Test & Abnahme

Für das **Projektmanagement** sind folgende Tools im Einsatz:

| Bereich                | Tool                                                       |
|------------------------|------------------------------------------------------------|
| Vorgehensmodell: Scrum | ScrumDo                                                    |
| Datenaustausch         | GitHub                                                     |
| Dokumentation          | Markdown, Pandoc, Microsoft Office 2016, draw.io, plantuml |

Für die **Entwicklung** sind folgende Tools im Einsatz:

| Bereich                | Tool                                  |
| ---------------------- | ------------------------------------- |
| Entwicklungsumgebung   | ItelliJ IDEA 2017                     |
| Programmiersprache     | Java 1.8                              |
| Versionskontrolle      | git, GitLab                           |
| Testing                | JUnit, Integrationstests, Systemtests |
| Continuous Integration | Jenkins                               |

## Konfigurationsmanagement

Das Konfigurationsmanagement soll die Einhaltung von Regeln für einen organisatiorischen und verhaltensmässigen Lebenslauf eines Produkts und seiner Configuration Items (Konfigurationseinheiten) gewährleisten.

Ein Configuration Item ist eine beliebige Kombination aus Hardware, Software oder Dienstleistung. In diesem Projekt sind diese die Dokumentationen, Komponenten und Interfaces. Im Kapitel "Releasemanagement" sind diese ausführlich aufgelistet.

## Releasemanagement

Das Releasemanagement befasst sich mit der Planung und Durchführung der Veröffentlichung. Dieses Projekt beinhaltet zwei Releases:

- Release 1: Zwischenabgabe (SW08)
- Release 2: Schlussgabe (SW13)

| Configuration Item    | Release 1      | Release 2 |
| --------------------- | -------------- | --------- |
| Projektmanagementplan | 1.0.0          |           |
| Systemspezifikation   | 1.0.0          |           |
| Logger                | 1.0.0-SNAPSHOT |           |
| LoggerSetup           | 1.0.0-SNAPSHOT |           |
| LoggerCommon          | 1.0.0-SNAPSHOT |           |
| LoggerComponent       | 1.0.0-SNAPSHOT |           |
| LoggerServer          | 1.0.0-SNAPSHOT |           |
| StringPersistor       | 4.0.1          |           |
| StringPersistorFile   | 1.0.0-SNAPSHOT |           |
| GameOfLife            | 1.0.0-SNAPSHOT |           |

# Anhänge
## Testplan
### Testdesign & Abläufe
### Testfälle

## Sprintreviews
### Sprintreview 1
#### Protokoll Sprintreview

| Nr. / ID | Titel / Kurzbeschreibung | Version  | Status   |
| -------: | ------------------------ | -------- | -------- |
| 1        | Projektmanagementplan    | 1.0.0    | erledigt |
| 2        | Projektstrukturplan      | 1.0.0    | erledigt |
| 3        | Rahmenplan               | 1.0.0    | erledigt |
| 4        | Risikoanalyse            | 1.0.0    | erledigt |
| 5        | Scrum-Stories            | 1.0.0    | erledigt |

Im ersten Sprint konnten wir nicht alle Stories, welche ursprünglich für den ersten Sprint geplant waren, umsetzen.

Dies liegt daran, dass wir sehr viel Zeit für die Einarbeitung in den Stoff, die Gruppenorganisation und -kommunikation gebraucht haben. Weiterhin benötigte auch das Aufsetzen der Umgebung mitsamt allen Tools eine gewisse Zeit.

**Im ersten Sprint konnten wir also folgende Artefakte bereitstellen: Projektmanagementplan, Projektstrukturplan, Rahmenplan, Risikoanalyse, Scrum-Stories.**

#### Testprotokoll

Im Sprint 1 wurden noch keine Tests vorgenommen.

### Sprintreview 2

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
