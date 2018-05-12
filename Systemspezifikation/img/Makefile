all: game.png loggerinterface.png loggercomponent.png loggerserver.png loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png

game.png: game.uml
	plantuml -Tpng game.uml

loggerinterface.png: loggerinterface.uml
	plantuml -Tpng loggerinterface.uml

loggercomponent.png: loggercomponent.uml
	plantuml -Tpng loggercomponent.uml

loggerserver.png: loggerserver.uml
	plantuml -Tpng loggerserver.uml

loggerviewer.png: loggerviewer.uml
	plantuml -Tpng loggerviewer.uml

loggercommon.png: loggercommon.uml
	plantuml -Tpng loggercommon.uml

stringpersistor.png: stringpersistor.uml
	plantuml -Tpng stringpersistor.uml

komponentendiagramm.png: komponentendiagramm.uml
	plantuml -Tpng komponentendiagramm.uml

clean:
	rm -f game.png loggerinterface.png loggercomponent.png loggerserver.png loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png
