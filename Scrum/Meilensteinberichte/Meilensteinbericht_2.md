---
title: Meilensteinbericht - Meilenstein 2
subtitle: Version: 1.0.0
date: 07.04.2018
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# Meilensteinbericht - Meilenstein 2

### Meilensteinbericht 2

#### Zeitpunkt Meilenstein 2: Beginn SW08

#### Beschreibung Meilenstein 2

Der zweite Meilenstein wurde erreicht, nachdem das Logger-Interface zur Verfügung gestellt wurde, die Logger- und Stringpersistor-Komponenten implementiert wurden, der Logger-Server implementiert wurde und so die Aufgabe durchgeführt werden konnte, bestimmte Ereignisse der Applikation (Game of Life) mithilfe der selbstgeschriebenen Logger-Klasse zu loggen und die Logs mithilfe der Stringpersistor-Klasse zu persistieren.

#### Detail-Vorgaben zum Meilenstein 2:
##### Projektmanagement
###### Projektführung
- Der Ressourcenplan ist aktuell
- Die Projektkontrolle ist aktuell
- Der ProductBacklog für Meilenstein 2 steht
- Der SprintBacklog für Sprint 3 und 4 steht

###### Projektunterstützung
- Tools für Entwicklung, Test & Abnahme sind aktuell
- Konfigurationsmanagement ist aktuell
- Releasemanagement ist aktuell

###### Testplan
- Testdesign ist vollständig definiert
- Testfälle für implementierter Code sind vorhanden

###### Anhänge
- Meilensteinreview für Meilenstein 2 verfasst
- Sprintreview für Sprint 1 verfasst
- Sprintreview für Sprint 2 verfasst

##### Systemspezifikation
- TODO (weitere Infos folgen in späteren Wochen)

##### Applikation
- Der Logger-Komponente ist als Komponente mithilfe von Interfaces austauschbar
- Die Applikation (Game of Life) gibt bei jedem Log einen Message-Level mit
- Die API der Logger-Komponente kann einen Level-Filter setzen, um die zu übertragenden Logs einzuschränken
- Der Level-Filter kann während der Laufzeit geändert werden
- Die Logs werden durch Logger-Komponenten und den Logger-Server kausal und verlässlich aufgezeichnet
- Die Logger-Komponente ist austauschbar
- Die Logger-Komponente ist plattformunabhängig
- Der Komponentenaustausch ist ausserhalb der IDE und ohne Code-Anpassung (Neukompilation) möglich
- Es können mehrere Instanzen der Logger-Komponente parallel auf den Logger-Server loggen
- Die Logs können dauerhaft auf dem Logger-Server in einem einfachen und lesbaren Textfile gespeichert werden
- Das Textfile enthält mind. die Quelle der Logmeldung, den Zeitstempel der Erstellung, den Zeitstempel beim Erreichen beim Server, den Message-Level und den Message-Text
- Das Schreiben des Textfiles erfolgt serverseitig unter der Verwendung der StringPersistor-Schnittstelle
- Die StringPersistorFile-Komponente persistiert die Logs
- Die Daten werden in strukturierter Form dem Payload-Parameter der StringPersistor-Schnittstelle unter Verwendung des Adapter-Pattern übergeben

#### Meilensteinziele:
1. Ein Logger-Interface zur Verfügung stellen
2. Die Logger-Komponente implementieren
3. Den Logger-Server implementieren
4. Die Stringpersistor-Komponente implementieren
5. Ereignisse der Applikation (Game of Life) über das Logger-Interface mit der Logger-Komponente (auf dem Logger-Server mittels Stringpersistor) loggen

#### Wurden die Meilensteinziele erreicht?
1. Ja.
2. Ja.
3. Ja.
4. Ja.
5. Ja.