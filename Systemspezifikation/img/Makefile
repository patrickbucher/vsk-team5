all: stringpersistor.png stringpersistor.svg komponentendiagramm.png komponentendiagramm.svg

stringpersistor.png: stringpersistor.uml
	plantuml -Tpng stringpersistor.uml

stringpersistor.svg: stringpersistor.uml
	plantuml -Tsvg stringpersistor.uml

komponentendiagramm.png: komponentendiagramm.uml
	plantuml -Tpng komponentendiagramm.uml

komponentendiagramm.svg: komponentendiagramm.uml
	plantuml -Tsvg komponentendiagramm.uml

clean:
	rm -f stringpersistor.png stringpersistor.svg komponentendiagramm.png komponentendiagramm.svg
