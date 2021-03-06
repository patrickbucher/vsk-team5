---
title: Dokumentationsreview der Gruppe 6
subtitle: Version 1.0.0
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# Einleitung

- Datum: *13.05.2018*
- Review durchgeführt von: *Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)*
- Reviewte Dokumente erstellt von: *Gruppe 6 (Tim Bolzen, Tobias Jäggi, Pascal Keusch, Roman Schraner)*

**Anmerkung:** Das Review bezieht sich auf den uns eingereichten Stand vom 12.05.2018. Der nachfolgenden Tabelle können Versionsnummern und Daten der reviewten Dokumente entnommen werden.

| Dokument              | Version | Datum      |
| --------------------- | ------- | ---------- |
| Projektmanagementplan | 1.0.3   | 09.04.2018 |
| Systemspezifikation   | 1.2.0   | 31.04.2018 |
| Sprintreview 1        | n. a.   | 26.03.2018 |
| Sprintreview 2        | n. a.   | 09.04.2018 |
| Sprintreview 3        | n. a.   | 30.04.2018 |
| Sprintreview 4        | n. a.   | 30.04.2018 |

# Projektmanagementplan

Dieses Kapitel befasst sich mit dem Projektmanagementplan. Die Kritikpunkte stützen sich auf der SoDa-Vorlage, dem Erlernten aus den Modulen *Project Management Basics* und *Verteilte Systeme und Komponenten* sowie Quellen aus dem Internet.

## Anmerkungen und  Verbesserungsvorschläge

- Kapitel 1.2 Projektstrukturplan: Die vorliegende Grafik entspricht eher einem Rahmenplan als einem Projektstrukturplan. Ein Projektstrukturplan teilt ein Projekt in kleinere, besser durchführ- und kontrollierbare Teilprojekte und diese wiederrum in Arbeitspakete auf ([Quelle](https://www.projektmanagementhandbuch.de/handbuch/projektplanung/projektstrukturplan/)).
- Kapitel 2.1 Rahmenplan: Es empfiehlt sich, die Stories vollständig in ScrumDo zu erfassen und im Dokument lediglich darauf zu verweisen.

# Systemspezifikation

Dieses Kapitel befasst sich mit der Systemspezifikation. Die Kritikpunkte stützen sich auf der SoDa-Vorlage, dem Erlernten aus den Modulen *Objektorientes Programmieren*, *Algorithmen und Datenstrukturen* und *Verteilte Systeme und Komponenten* sowie Quellen aus dem Internet.

## Anmerkungen und Verbesserungsvorschläge

- Allgemein
    - Die Zweite Seite (komplett leer) hat in der Kopfzeile die Bezeichung "Projektmanagementplan".
    - Die Seitennummerierung fehlt komplett.
    - Es fehlen noch einige Abschnitte und Abbildungen, diese sind aber entsprechend markiert.
- Architektur/Designentscheide 
    - zu 2.3.2: LogConverterStrategy:
        - Wie kann die Logger-Stretegy ausgetauscht werden?
    - Wie sieht eine Logdatei beispielsweise aus?
- TCP-Protokoll
    - zu 3.2.5: "Nachdem die TCP Verbindung aufgebaut wurde, dürfen beide Parteien anfangen, nach Belieben Pakete zu versenden."
        - Dürfen Acknowledgment-Pakete auch geschickt werden, wenn zuvor nichts erhalten wurde?
    - Was passiert, wenn die Integer-Variable `position` überläuft?
- Environment-Anforderungen
    - Wie wird die Erreichbarkeit des Logger-Servers für den Viewer konfiguriert?
- Testing
    - Gibt es einen Benchmark-Test für den StringPersistor?
    - Wie hoch ist die Testabdeckung, und wie wird diese beurteilt?
    - Gibt es keine Integrationstests oder sind die einfach noch nicht dokumentiert?

# Sprintreviews

Dieses Kapitel fasst sämtliche Sprintreviews zusammen. Deswegen ist weder eine Versionsnummer, noch ein Datum angegebenen.  Die Kritikpunkte stützen sich auf der SoDa-Vorlage.

## Anmerkungen und Verbesserungsvorschläge

- Die Daten der Sprintreviews 3 und 4 sind gleich. Das Sprintreview 4 scheint zwei Wochen zu früh datiert zu sein.
