# VSK – Antworten zu ein paar Fragen auf Studentbox

#### Threads:
- Wo wurden im Projekt Threads eingesetzt? Bei der TCP-Schnittstelle zwischen LoggerComponent und LoggerServer. LoggerServer implementiert einen ConcurrentClientHandler, damit wird für jeden Client einen neuen Thread erzeugt
- Wie bewerten Sie die Möglichkeit des Beendens eines Threads? Wird einer der Sockets clientseitig geschlossen, tritt eine EOFExeption auf, die run()-Methode wird wird mittels einem Return beendet

#### Observer-Pattern:
- Wo wurde es im Projekt eingesetzt? Bei der RMI-Implementation zwischen LoggerViewer und LoggerServer. Die zwei Interfaces RemoteRegistration und RemotePushHandler implementieren das Observer-Pattern: LoggerServer implementiert RemoteRegistration und registriert so neue Viewer, LoggerViewer implementiert via der Klasse LoggerModel das Interface RemotePushHandler um Meldungen per Push-Verfahren zu erhalten. Die beiden Klassen sind so besser voneinander abgekoppelt

#### Middleware:
- Welche Middleware wurde im Projekt verwendet / Welche Middleware wurde im Projekt eingesetzt? RMI
- Was ist RMI-Client und was RMI-Server? LoggerViewer und ConcurrentLoggerServer
-	Wo entsteht das Binding? Bei der Registration von Viewern beim Server durch die RemoteRegistration-Schnittstelle. Klasse LoggerViewer benutzt Methode registerViewer().
-	Wo entsteht das Lookup? Jeder Client hat ein ConcurrentClientHandler. Dieser läuft mit einem eigenen Thread, so dass mehrere Viewer das Logging anzeigen können. Wenn beim Client (ConcurrentClientHandler) eine Message erscheint, wird der Client vom ConcurrentClientHandler benachrichtigt

#### Design-Patterns:
-	Wo im Projekt wurden Design-Patterns verwendet und wo wurde dies dokumentiert?
o	Adapter-Pattern: Die Klasse StringPersistorAdapter implementiert das Adapter-Pattern, indem es das LogPersistor-Interface implementiert und die Message in das Format der vorgegebenen Schnittstelle StringPersistor umschreibt
-	Factory-Pattern: Die Klasse StringPersistorAdapter besitzt einen private Konstruktor und kann nur via der Methode create() mittels einem Factory-Pattern erzeugt werden.
-	Strategy-Pattern: Interface LogMessageFormatter wird von allen Formattern (SimpleFormatter und CurlyFormatter) implementiert. Wenn ein neues StringPersistorAdapter-Objekt erzeugt wird (via Factory-Pattern, siehe oben) muss ein LogMessageFormatter als Argument mitgegeben werden
-	Observer-Pattern: Siehe oben
-	Singleton-Pattern: Die Klasse MessageFormatter wird durch ein Singleton-Pattern instanziert. Die Klasse ist mit private static final und der Konstruktor mit final static markiert. Sie verfügt über eine Methode getInstance() welche prüft, ob bereits ein Objekt vorhanden ist und falls kein Objekt vorhanden ist eines erzeugt. Dann gibt sie als Antwort das MessageFormatter-Objekt zurück

#### Planen und Schätzen:
-	Wo sind Schätzfehler im Projekt passiert? Wo ist das in der Doku ersichtlich? Bei den Sprints mussten Scrum-Stories geschätzt werden. In den Sprint-Reviews wurden dabei ein geschätzter und ein tatsächlicher Wert festgehalten

#### Configuration-Items:
-	Im Projekt zeigen, wo Configuration-Items festgehalten sind: Im Projekt gibt es 2 Arten von Konfigurations-Artefakten: 1) Logger-Konfiguration und 2) Security-Policy für RMI
-	(Expemplarisch) Logger-Konfiguration:
	-	Angabe der Jar-Datei, welche Loggerkomponente enthält (Implementierung von LoggerComponent und LoggerComponent-Setup)
	-	Angabe des Log-Levels
	-	Angabe der Server-Koordinaten

#### Komponenten:
-	Wo sieht man die Aufteilung des Systems in Komponenten? Beispielsweise Logger-Client-Application besteht aus den 3 Komponenten Game, LoggerComponent und StringPersistorFile
-	Wie ist diese Aufteilung entstanden? Austauschbarkeit der Logger-Komponente war vorgegeben
-	Wo (in welchem Dokument) sucht man die Spezifikation der Komponenten? SysSpec
Uhrensynchronisation:
-	Wie wurde es implementiert im Projekt? Alle involvierten Rechner müssen ihre Systemzeit per NTP (Network Time Protocol) synchronisieren. Anwendungsseitig wurde keine Uhrensynchronisation implementiert. Es wurde auch keine Kausalitätsprüfung implementiert, die feststellt, ob ein bestimmter Zeitstempel vor einem anderen liegt. In der Anwendung wird für die Zeitstempel java.time.instant verwendet.

