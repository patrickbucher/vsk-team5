clean:
	rm -rf client
	rm -f .build-client

.build-client build-client:
	mvn -f ../g05-logger install
	mvn install
	rm -rf client
	mkdir client
	cp ~/.m2/repository/ch/hslu/vsk18fs/loggerinterface/1.0.0-SNAPSHOT/loggerinterface-1.0.0-SNAPSHOT.jar client/loggerinterface.jar
	cp ~/.m2/repository/ch/hslu/vsk18fs/g05/g05-loggercommon/1.0.0-SNAPSHOT/g05-loggercommon-1.0.0-SNAPSHOT.jar client/logger-common.jar
	cp ~/.m2/repository/ch/hslu/vsk18fs/g05/g05-game/1.0.0-SNAPSHOT/g05-game-1.0.0-SNAPSHOT.jar client/game.jar
	cp ~/.m2/repository/ch/hslu/vsk/stringpersistor-api/4.0.1/stringpersistor-api-4.0.1.jar client/stringpersistor-api.jar
	cp ../g05-stringpersistor/target/g05-stringpersistor-1.0.0-SNAPSHOT.jar client/stringpersistor.jar
	cp config.xml client/
	touch .build-client

run-client: .build-client
	java -cp "client/loggerinterface.jar:client/logger-common.jar:client/stringpersistor-api.jar:client/stringpersistor.jar:client/game.jar" "org.bitstorm.gameoflife.StandaloneGameOfLife"

zip-client: .build-client
	zip -r client.zip client/
