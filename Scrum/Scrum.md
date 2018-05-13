---
title: Scrum-Dokumente
subtitle: Version 1.2.1
date: 13.05.2018
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# TODOs

- Stories entfernen + Verweis auf ScrumDo

# Stories (nach Epics)

## Epic Log-Ereignisse

1. Aufzeichnung Spielverlaufs: Als *Anwender* möchte ich *persistent festgehaltene Spielereignisse*, um *den Spielverlauf bei Bedarf nachvollziehen zu können*.
2. Aufzeichnung von Problemen: Als *Anwender* möchte ich *persistent festgehaltene Fehlermeldungen*, um *auftretende Probleme nachweisen zu können*.
3. Aufzeichnung von Exceptions: Als *Entwickler* möchte ich *persistent festgehaltene StackTraces*, um *geworfene Exceptions nachvollziehen zu können*.

## Epic Log-Level

4. Message-Level: Als *Entwickler* möchte ich *zu jeder Log-Meldung ein Message-Level setzen können*, um *Log-Meldungen auf verschiedenen Levels definieren zu können*.
5. Codiertes Filter-Level: Als *Entwickler* möchte ich *zur Laufzeit einen Filter-Level setzen können*, um *die Ausgabe der Log-Meldungen steuern zu können*.
6. Konfiguriertes Filter-Level: Als *Administrator* möchte ich *vor der Ausführung einen Filter-Level konfigurieren können*, um *die Ausgabe der Log-Meldungen steuern zu können*.

## Epic Log-Interface

7. Logger-Interface: Als *Entwickler* möchte ich *ein definiertes Logger-Interface zur Verfügung haben*, um *eine Logger-Komponente umsetzen zu können*.
8. Logger: Als *Entwickler* möchte ich *einen `Logger` zur Verfügung haben*, um *Log-Meldungen aus dem Game ausgeben zu können*.
9. Logger-Setup: Als *Entwickler* möchte ich *ein `LoggerSetup` zur Verfügung haben*, um *Zugriff auf eine `Logger`-Instanz zu bekommen*.

## Epic Architektur

10. Komponenten-Austausch: Als *Administrator* möchte ich *eine austauschbare und plattformunabhängige Logger-Komponente*, um *die Logger-Komponente zur Laufzeit ohne Code-Anpassungen und Neukompilation austauschen zu können*.
11. StringPersistor: Als *Entwickler* möchte ich *eine `StringPersistor`-Implementierung für Textdateien (`StringPersistorFile`) zur Verfügung haben*, um *geloggte Meldungen persistent in Textdateien festhalten zu können*.
12. Mehrere Log-Clients: Als *Administrator* möchte ich *einen Logger-Server, auf den mehrere Logger-Komponenten parallel loggen können*, um *einen Server für mehrere Clients zur Verfügung stellen zu können*.
13. Payload-Adapter: Als *Entwickler* möchte ich *einen Adapter für die Übergabe des Payloads*, um *Daten in strukturierter Form dem `StringPersistor` übergeben zu können*.
14. Adapter-Tests: Als *Entwickler* möchte ich *Unittests für den Adapter haben*, um *zu sehen, ob Änderungen am Adapter zu Problemen führen*.
15. Speicherformat-Strategien: Als *Entwickler* möchte ich *über leicht austauschbare Log-Strategien verfügen*, um *das Speicherformat für Textdateien einfach auf Code-Ebene wählen zu können*.

## Epic Persistenz

16. Log-Aufzeichnung: Als *Administrator* möchte ich *eine verlässliche Aufzeichnung der Log-Ereignisse auf dem Server*, um *die Ereignisse aus dem Spiel in korrekter Reihenfolge nachverfolgen zu können*.
17. Unterscheidung Log-Clients: Als *Administrator* möchte ich *nach Client-Instanz unterscheidbare Logdateien*, um *die Log-Meldungen verschiedener Clients auseinanderhalten zu können*.
18. Textdatei-Log: Als *Administrator* möchte ich *Log-Ereignisse in einfachen Textdateien festhalten*, um *diese mit gängigen Werkzeugen betrachten und auswerten zu können (`grep`, `tail`, etc.)*.

## Epic Format

19. Speicherung Log-Quelle: Als *Administrator* möchte ich *die Quelle einer Logmeldung sehen*, um *verschiedene Clients und Sessions voneinander unterscheiden zu können*.
20. Speicherung Log-Level: Als *Administrator* möchte ich *den Level einer Logmeldung sehen*, um *diese je nach Bedarf nachträglich filtern zu können*.
21. Speicherung Ereignis-Zeitstempel: Als *Administrator* möchte ich *den Zeitstempel der Message-Erstellung* sehen, um *den genauen Zeitpunkt des Ereignisses zu kennen*.
22. Speicherung Log-Zeitstempel: Als *Administrator* möchte ich *den Zeitstempel des Message-Eingangs auf dem Server* sehen, um *Verzögerungen beim Logging-Vorgang erkennen zu können*.
23. Speicherung Message-Text: Als *Administrator* möchte ich *den Text der Log-Message auf dem Server sehen*, um *das aufgetretene Ereignis erkennen zu können*.

## Epic Konnektivität

24. Konfiguration Serverzugriff: Als *Administrator* möchte ich *die Erreichbarkeit des Servers mittels Konfigurationsdatei definieren können*, um *die Verbindung zum Log-Server ohne Quellcodeanpassung definieren zu können*.
25. Robuste Netzwerkanwendung: Als *Anwender* möchte ich *eine Anwendung mit robuster Netzwerkkonnektivität*, um *auch bei Netzwerkunterbrüchen einen ununterbrochenen Spielfluss ohne verlorene Log-Meldungen zu haben*.
26. Message-Queue: Als *Entwickler* möchte ich *auftretende Log-Ereignisse in einer Message-Queue zwischenspeichern können*, um *Log-Ereignisse bei einem Verbindungsunterbruch erneut senden zu können*.

## Epic Viewer

27. Viewer: Als *Anwender* möchte ich *einen Viewer für Logmeldungen haben*, um *Logmeldungen ohne Kenntnis des physischen Speicherorts auf dem Server betrachten zu können*.

## Rollen

- Ein *Entwickler* hat Zugriff auf den Quellcode und verfügt über die Fähigkeiten und Berechtigungen um Änderungen daran vorzunehmen.
- Ein *Administrator* hat Zugang auf beide konzeptionell involvierten Systeme (Client und Server) und Zugriff auf die Konfigurationsdateien aller involvierter Komponenten.
- Ein *Anwender* kann das Spiel auf dem Client ausführen, kann aber keine Änderungen an Quellcode und Konfigurationsdateien vornehmen und nicht auf den Server zugreifen.

# Sprintreviews

## Sprintreview - Sprint 1

### Protokoll Sprintreview

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

## Sprintreview - Sprint 2

### Protokoll Sprintreview

| Nr. / ID | Dokument                 | Version  | Status   |
| -------: | ------------------------ | -------- | -------- |
| 1        | Testplan                 | 1.0.0    | erledigt |
| 2        | TCP-Schinttstelle        | 1.0.0    | erledigt |

| Nr. / ID | Scrum-Story (ScrumDo)                           | Status |
| -------: | ----------------------------------------------- | ------ |
| V6-10    | Komponentenaustausch                            | Doing  |
| V6-28    | Logger-Aufrufe zum Festhalten des Spielverlaufs | Done   |
| V6-29    | Logger-Aufrufe für Aufzeichnung von Problemen   | Done   |
| V6-01    | Aufzeichnung des Spielverlaufs                  | Done   |
| V6-04    | Message-Level                                   | Done   |
| V6-17    | Unterscheidung Log-Clients                      | Done   |
| V6-13    | Payload-Adapter                                 | Done   |
| V6-20    | Speicherung Log-Level                           | Done   |
| V6-22    | Speicherung Log-Zeitstempel                     | Done   |
| V6-14    | Adapter-Tests                                   | Done   |
| V6-21    | Speicherung Ereignis-Zeitstempel                | Done   |
| V6-08    | Logger                                          | Done   |
| V6-11    | StringPersistor                                 | Done   |
| V6-02    | Aufzeichnung von Problemen                      | Done   |
| V6-09    | Logger-Setup                                    | Done   |
| V6-18    | Textdatei-Log                                   | Done   |
| V6-30    | TCP-Netzwerk-Interface                          | Done   |
| V6-24    | Konfiguration Serverzugriff                     | Done   |
| V6-23    | Speicherung Message-Text                        | Done   |
| V6-03    | Aufzeichnung von Exceptions                     | Done   |
| V6-05    | Codiertes Filter-Level                          | Done   |
| V6-07    | Logger-Interface                                | Done   |
| V6-06    | Konfiguriertes Filter-Level                     | Done   |

Im zweiten Sprint konnten sämtliche Scrum-Stories umgesetzt werden, welche für diesen Sprint vorgesehen waren. Zusätzlich konnte der Rückstand aus Sprint 1 aufgeholt werden. Als Artefakte konnten der Testplan und die TCP-Schnittstelle dokumentiert werden.

**Im zweiten Sprint konnten wir also folgende Artefakte bereitstellen: _Testplan_ und _TCP-Schnittstelle_. Weiterhin konnten von 23 Scrum-Stories 22 fertiggestellt werden. Der Komponentenaustausch steht noch aus.**

## Sprintreview - Sprint 2

### Protokoll Sprintreview

| Nr. / ID | Dokument                 | Version  | Status   |
| -------: | ------------------------ | -------- | -------- |
| 1        | Testplan                 | 1.0.0    | erledigt |
| 2        | SchnittstelleTCP         | 1.0.0    | erledigt |

| Nr. / ID | Scrum-Story (ScrumDo)                           | Status |
| -------: | ----------------------------------------------- | ------ |
| V6-10    | Komponentenaustausch                            | Doing  |
| V6-28    | Logger-Aufrufe zum Festhalten des Spielverlaufs | Done   |
| V6-29    | Logger-Aufrufe für Aufzeichnung von Problemen   | Done   |
| V6-01    | Aufzeichnung des Spielverlaufs                  | Done   |
| V6-04    | Message-Level                                   | Done   |
| V6-17    | Unterscheidung Log-Clients                      | Done   |
| V6-13    | Payload-Adapter                                 | Done   |
| V6-20    | Speicherung Log-Level                           | Done   |
| V6-22    | Speicherung Log-Zeitstempel                     | Done   |
| V6-14    | Adapter-Tests                                   | Done   |
| V6-21    | Speicherung Ereignis-Zeitstempel                | Done   |
| V6-08    | Logger                                          | Done   |
| V6-11    | StringPersistor                                 | Done   |
| V6-02    | Aufzeichnung von Problemen                      | Done   |
| V6-09    | Logger-Setup                                    | Done   |
| V6-18    | Textdatei-Log                                   | Done   |
| V6-30    | TCP-Netzwerk-Interface                          | Done   |
| V6-24    | Konfiguration Serverzugriff                     | Done   |
| V6-23    | Speicherung Message-Text                        | Done   |
| V6-03    | Aufzeichnung von Exceptions                     | Done   |
| V6-05    | Codiertes Filter-Level                          | Done   |
| V6-07    | Logger-Interface                                | Done   |
| V6-06    | Konfiguriertes Filter-Level                     | Done   | 

Im zweiten Sprint konnten sämtliche Scrum-Stories umgesetzt werden, welche für diesen Sprint vorgesehen waren. Zusätzlich konnte der Rückstand aus Sprint 1 aufgeholt werden. Als Artefakte konnten der Testplan und die TCP-Schnittstelle dokumentiert werden. <br>

**Im zweiten Sprint konnten wir also folgende Artefakte bereitstellen: Testplan und SchnittstelleTCP. Weiterhin konnten von 23 Scrum-Stories 22 fertiggestellt werden. Der Komponentenaustausch steht noch aus.**

## Sprintreview - Sprint 3

### Protokoll Sprintreview

| Nr. / ID | Scrum-Story (ScrumDo)                              | Zeit (geschätzter Wert) | Zeit (effektiver Wert) | Status |
| -------: | -------------------------------------------------- | ----------------------- | ---------------------- | ------ |
| V6-38    | Log-Meldungen im Viewer sortieren                  | 4 h                     | 3 h                    | Done   |
| V6-37    | Viewer-GUI                                         | 8 h                     | 9 h                    | Done   |
| V6-39    | Projektstruktur für logger-viewer bereitstellen    | 1 h                     | 0 h 30                 | Done   |
| V6-33    | Server: Fehler im Endlosloop                       | 4 h                     | 1 h                    | Done   |
| V6-32    | Spiel kann nur noch schrittweise ausgeführt werden | 2 h                     | 1 h                    | Done   |
| V6-27    | Viewer                                             | 8 h                     | 7 h                    | Done   |
| V6-31    | LoggerComponentSetupTest korrigieren               | 4 h                     | 1 h                    | Done   |

**Im dritten Sprint wurden keine Artefakte bereitstellen. Es wurden insgesamt 7 Scrum-Stories umgesetzt.**

## Sprintreview - Sprint 4

##3 Protokoll Sprintreview

| Nr. / ID | Dokument                 | Version  | Status   |
| -------: | ------------------------ | -------- | -------- |
| 1        | RMI-Policy               | 1.0.0    | erledigt |

| Nr. / ID | Scrum-Story (ScrumDo)                                       | Zeit (geschätzter Wert) | Zeit (effektiver Wert) | Status |
| -------: | ----------------------------------------------------------- | ----------------------- | ---------------------- | ------ |
| V6-35    | Konfiguration Logger-Verzeichnis (und optional Server-Port) | 1 h 30                  | -                      | Todo   |
| V6-10    | Komponenten-Austausch                                       | 8 h                     | -                      | Todo   |
| V6-42    | RMI-Kommunikation umsetzen                                  | 4 h                     | 8 h                    | Done   |
| V6-16    | Log-Aufzeichnung                                            | 4 h                     | 3 h                    | Done   |
| V6-40    | Unsolide Validierungslogik von Log-Meldungen                | 0 h 30                  | 0 h 15                 | Done   |
| V6-34    | Integrationstests als solche markieren                      | 0 h 30                  | 0 h 15                 | Done   |
| V6-41    | Message-Format für Logger erweitern                         | 1 h                     | 0 h 30                 | Done   |
| V6-28    | Logger-Aufrufe zum Festhalten des Spielverlaufs             | 2 h                     | 2 h 30                 | Done   |
| V6-29    | Logger-Aufrufe für Aufzeichnung von Problemen               | 1 h                     | 1 h 30                 | Done   |
| V6-01    | Aufzeichnung des Spielverlaufs                              | 2 h                     | 3 h                    | Done   |
| V6-19    | Logger-Aufrufe für Aufzeichnung von Problemen               | 2 h                     | 1 h                    | Done   |
| V6-26    | Temporäre Zwischenspeicherung                               | 8 h                     | 9 h                    | Done   |
| V6-36    | RMI-Interface definieren                                    | 4 h                     | 2 h                    | Done   |
| V6-25    | Robuste Netzwerkanwendung                                   | 2 h                     | 4 h                    | Done   |
| V6-12    | Mehrere Log-Clients                                         | 2 h                     | 4 h                    | Done   |
| V6-15    | Speicherformat-Strategien                                   | 4 h                     | 4 h                    | Done   |

**Im letzten Sprint konnten wir folgendes Artefakt bereitstellen: RMI-Policy. Weiterhin konnten von 16 Scrum-Stories 14 fertiggestellt werden. Der Komponentenaustausch wird am Ende des Sprint 4 ausgeführt.**