all: loggerinterface.png loggercomponent.png loggerserver.png loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png

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
	rm -f loggerinterface.png loggercomponent.png loggerserver.png loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png
