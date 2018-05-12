all: loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png

loggerviewer.png: loggerviewer.uml
	plantuml -Tpng loggerviewer.uml

loggercommon.png: loggercommon.uml
	plantuml -Tpng loggercommon.uml

stringpersistor.png: stringpersistor.uml
	plantuml -Tpng stringpersistor.uml

komponentendiagramm.png: komponentendiagramm.uml
	plantuml -Tpng komponentendiagramm.uml

clean:
	rm -f loggerviewer.png loggercommon.png stringpersistor.png komponentendiagramm.png
