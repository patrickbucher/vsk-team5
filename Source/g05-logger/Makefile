SERVER=10.155.101.146

clean:
	rm -rf server
	rm -f .build-server
	rm -rf viewer
	rm -f .build-viewer

.build-server build-server:
	mvn -f ../g05-stringpersistor install
	mvn install
	rm -rf server
	mkdir server
	cp ~/.m2/repository/ch/hslu/vsk/stringpersistor-api/4.0.1/stringpersistor-api-4.0.1.jar server/stringpersistor-api.jar
	cp ../g05-stringpersistor/target/g05-stringpersistor-1.0.0-SNAPSHOT.jar server/stringpersistor.jar
	cp logger-common/target/g05-loggercommon-1.0.0-SNAPSHOT.jar server/logger-common.jar
	cp logger-server/target/g05-loggerserver-1.0.0-SNAPSHOT.jar server/logger-server.jar
	touch .build-server
	cp logger-server/fake.policy fake.policy

.build-viewer build-viewer:
	mvn -f ../g05-stringpersistor install
	mvn install
	rm -rf viewer
	mkdir viewer
	cp ~/.m2/repository/ch/hslu/vsk/stringpersistor-api/4.0.1/stringpersistor-api-4.0.1.jar viewer/stringpersistor-api.jar
	cp ../g05-stringpersistor/target/g05-stringpersistor-1.0.0-SNAPSHOT.jar viewer/stringpersistor.jar
	cp logger-common/target/g05-loggercommon-1.0.0-SNAPSHOT.jar viewer/logger-common.jar
	cp logger-viewer/target/g05-loggerviewer-1.0.0-SNAPSHOT.jar viewer/logger-viewer.jar
	touch .build-viewer
	cp logger-viewer/fake.policy fake.policy

run-server: .build-server
	java -Djava.security.policy=fake.policy -Djava.rmi.server.hostname=$(SERVER) -cp "server/stringpersistor-api.jar:server/stringpersistor.jar:server/logger-server.jar:server/logger-common.jar" "ch.hslu.vsk18fs.g05.loggerserver.ConcurrentLoggerServer"

zip-server: .build-server
	zip -r server.zip server/

run-viewer: .build-viewer
	java -Djava.security.policy=fake.policy -cp "viewer/stringpersistor-api.jar:viewer/stringpersistor.jar:viewer/logger-viewer.jar:viewer/logger-common.jar" "ch.hslu.vsk18fs.g05.loggerviewer.LoggerViewer" $(SERVER)

zip-viewer: .build-viewer
	zip -r viewer.zip viewer/
