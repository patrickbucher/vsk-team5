---
title: TCP-Schnittstelle
subtitle: Verteilte Systeme und Komponenten
author: Gruppe 5 (Patrick Bucher, Pascal Kiser, Fabian Meyer, Sascha Sägesser)
---

# Konfiguration

TODO: erläutern

- clientseitig: siehe `config.xml`
- serverseitig: statisch (siehe Code)

# Protokoll

TODO: erläutern

- Payload: `ch.hslu.vsk.g05.loggercommon.Message`
- Client: `ch.hslu.vsk.g05.loggerserver.StupidClientHandler`
- Server: `ch.hslu.vsk.g05.loggerserver.StupidLoggerServer`
    - Client-Handler: `ch.hslu.vsk.g05.loggerserver.StupidClientHandler`

# Schwierigkeiten

- Der Socket des Loggers kann nicht explizit über die Schnittstelle geschlossen werden.
- Lösung mit Hack nötig (`null`-Konfiguration an `LoggerComponentSetup` übergeben, sodass dort der Socket geschlossen wird)
- Werden die Sockets nicht geschlossen, laufen die serverseitigen Handler-Threads immer weiter.
- `EOFException` beim serverseitigen Lesen signalisiert unterbrochene Socket-Verbindung.
