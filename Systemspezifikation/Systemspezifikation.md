---
title: Systemspezifikation
subtitle: Version 1.0.0
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# Systemübersicht

Die Systemarchitektur ist grösstenteils durch den Projektauftrag festgelegt. Dieses ist in der Abbildung [Komponentendiagramm Projektauftrag](#systemarchitektur) ersichtlich.

![Komponentendiagramm Projektauftrag](img/projektauftrag-komponentendiagramm.png){#systemarchitektur}

Die Anwendung besteht aus zwei Bereichen: Dem Client und dem Server. Das _Game_ kommuniziert mittels _Logger_- und _LoggerSetup_-Schnittstelle mit der _LoggerComponent_. Diese schlägt die Brücke zum Server-Bereich über ein TCP/IP-Protokoll, worüber sie mit dem _LoggerServer_ kommuniziert. (Dieses Protokoll ist implementierungsspezifisch und bei der Gruppe 5 im Dokument _TCP-Schnittstelle_ dokumentiert.) Der _LoggerServer_ macht Gebrauch von einer Komponente namens _StringPersistorFile_, mit welcher er über die _StringPersistor_-Schnittstelle kommuniziert.

## Implementierungsspezifische Komponentenarchitektur

Die Abbildung [Komponentendiagramm](#komponentendiagramm) gibt das Komponentendiagramm aus dem Projektauftrag leicht verändert wieder, indem es dem Umstand Rechnung trägt, dass der _LoggerServer_  und die Komponente _StringPersistorFile_ nicht direkt über die _StringPersistor_-Schnittstelle, sondern per _LogPersistor_-Schnittstelle über den _StringPersistorAdapter_ und die _StringPersistor_-Schnittstelle miteinander kommunizieren.

![Komponentendiagramm](img/komponentendiagramm.png){#komponentendiagramm}

## Kontextdiagramm

![Kontextdiagramm](img/kontextdiagramm.png){#kontextdiagramm}

Die Abbildung [Kontextdiagramm](#kontextdiagramm) bietet einen abstrakten Überblick über das System und dessen Kontext. Zum System gehört die gesammte Applikation.

Die Schnittstelle zwischen dem System und dem Benutzer stellt z.B. ein Computer dar, auf dem der Benutzer dann das _Game of Life_ spielen und gleichzeitig loggen kann. Zusätzlich gehört die Aufgabenstellung, sprich der _LoggerProjektauftrag_, zum Kontext, da das System auf Basis dessen entwickelt wird.

# Architektur und Designentscheide

Die Architektur wurde weitgehendst vom Auftraggeber vorgegeben. Diese können grösstenteils im Dokument _Projektauftrag_ nachgelesen werden bzw. sind bereits eingangs in diesem Dokument aufgeführt.

## Modelle und Sichten

Beim Formulieren der Scrum-Stories (siehe Dokument _Scrum_) wurde die Anwendung von drei Perspektiven aus betrachtet:

- Ein _Anwender_ führt die die _Game of Life_-Applikation aus und will seinen Spielstand geloggt wissen.
- Ein _Administrator_ führt Konfigurationsarbeiten aus und will Parameter wie Log-Level und Serverkoordinaten einstellen können.
- Ein _Programmierer_ will die Software-Komponenten zur Verfügung haben, um damit die Anforderungen von _Anwender_ und _Administrator_ umsetzen zu können: z.B. eine Logger-Komponente mit entsprechenden Interface, damit der die Logger-Aufrufe in die Anwendung einführen kann.

Da alle Projektmitarbeiter und selbst die Auftraggeber die Anwendung aus allen der genannten Perspektiven betrachten und diese gar von innen her kennen, erübrigt sich eine tiefgehende Diskussion über Perspektiven.

## Datenstrukturen

Für den Austausch von Log-Meldungen wurden zahlreiche Datenstrukturen definiert:

- `Message` ist eine serialisierbare Klasse im `logger-common`-Projekt und dient als Austauschcontainer für Log-Meldungen zwischen `LoggerComponent` und `LoggerServer`. Sie besteht nur aus `String`-Attributen.
- Die Schnittstelle `LogMessage` und deren Implementierung `LogEntry` dienen als Austauschcontainer für Log-Meldungen zwischen `LoggerServer` und `StringPersistor` über den `StringPersistorAdapter`. Diese kann flexibel mit verschiedenen `LogMessage`-Implementierungen umgehen.
- Die Klasse `PersistedString` dient zum Abspeichern und späteren Auslesen von Log-Meldungen durch den `StringPersistor`.

Wie diese Datenstrukturen genau von den verschiedenen Komponenten verwendet werden, kann dem Dokument _Klassendiagramm_ entnommen werden.

# Schnittstellen

Im Rahmen des vorliegenden Projektes wurden mehrere Schnittstellen definiert. Diese wurden einerseits vom Auftraggeber festgelegt (`StringPersistor`) bzw. in den Interface-Kommitees definiert (`Logger` und `LoggerSetup`), wobei der Auftraggeber den entsprechende Rahmen vorgegeben hat; andererseits in den jeweiligen Projektteams ausgearbeitet.

## Externe Schnittstellen

Um einen Austausch der Logger-Komponente mit Implementierungen anderer Gruppen zu ermöglich, wurden folgeden Schnittstellen definiert:

| Schnittstelle     | Version        |
|-------------------|----------------|
| `Logger`          | 1.0.0-SNAPSHOT |
| `LoggerSetup`     | 1.0.0-SNAPSHOT |
| `StringPersistor` | 4.0.1          |

Die Schnittstellen `Logger`, `LoggerSetup` und `StringPersistor` werden hier nicht weiter beschrieben. Dere Dokumentation finden sich auf ILIAS. Von Java 1.8 zur Verfügung gestellte Schnittstellen sind hier nicht aufgeführt.

## Interne Schnittstellen

Neben den externen, von aussen vorgegebenen Schnittstellen enthielt der Projektauftrag auch Anforderungen, welche die Definition weiterer Schnittstellen erforderlich machten. Dies sind:

| Schnittstelle  | Version        |
|----------------|----------------|
| `LogPersistor` | 1.0.0-SNAPSHOT |
| TCP-Protokoll  | 1.0.0-SNAPSHOT |

Die Schnittstelle `LogPersistor` wurde selber definiert. Sie bildet die Grundlage für den `StringPersistorAdapter` und stellt dem `LoggerServer` eine für diesen geeigneten Schnittstelle zum `StringPersistor` dar. Hierzu wurde das Adapter-Pattern (GoF 139) implementiert. Auf das TCP-Protokoll wird im Dokument _TCP-Schnittstelle_ näher eingegangen.

# Implementierung

Auf die Implementierung der Anwendung soll an dieser Stelle nicht weiter eingegangen werden. Stattdessen sei hier auf die JavaDoc und auf das umfassende Klassendiagramm (Dokument _Klassendiagramm_) hingewiesen. Weitere Hinweise zur Implementierung finden sich in den Dokumenten _Testplan_ und _TCP-Schnittstelle_.

## Zwischenabgabe

Für die Zwischenabgabe ist weiter zu beachten, dass die gegenwärtige Implementierung _nicht_ für die persistente Aufzeichnung von Logmeldungen ausgelegt ist. Die Logdateien werden derzeit im temporären Verzeichnis des jeweiligen Benutzers gehalten. Dies führt dazu, dass bei einem Systemneustart sämtliche Daten verloren gehen. Für die Schlussabgabe soll das System dahingehend erweitert werden, dass das Log-Verzeichnis auf dem Server konfigurierbar ist.

# Environment-Anforderungen

Zum Ausführen der Anwendung wird client- wie serverseitig die [Java SE Runtime Environment 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) benötigt. Tests mit dern Open-Source-Variante _OpenJDK 8_ sind problemlos verlaufen.

Zum Kompilieren der Anwendung wird Maven und das HSLU-Nexus-Repository benötigt. Weiter wurde zum bequemen Packen und Ausführen von Client und Server jeweils ein `Makefile` geschrieben, das sich im Wurzelverzeichnis des Projekts `g05-game` (Client) bzw. `g05-logger` (Server) befindet[^make].

Zum Ausführen der Anwendung auf zwei verschiedenen Rechnern (Client und Server) wird eine funktionierende Netzwerkverbindung zwischen diesen beiden verlangt. Die beiden Systeme sollten zum gleichen Netzwerk gehören, da eine wechselseitige TCP-Kommunikation über einen Port `>1024` oftmals von Firewalls (d.h. an den Netzwerkgrenzen) unterbunden wird.

[^make]: Ironischerweise ist Maven angetreten um `ant` zu ersetzen, welches wiederum angetreten ist um `make` zu ersetzen. Die Lösung mit dem `Makefile` macht das Zusammensuchen und Ausführen (mit der entsprechenden `-classpath`-Option aber äusserst bequem, da `make` die Vorzüge leichtgewichtigen Dependency-Managements und der Shell kombiniert.
