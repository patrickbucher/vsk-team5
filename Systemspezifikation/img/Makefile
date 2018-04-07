all: klassendiagramm.pdf klassendiagramm.png klassendiagramm.svg komponentendiagramm.png komponentendiagramm.svg

klassendiagramm.pdf: klassendiagramm.svg
	convert klassendiagramm.svg klassendiagramm.pdf

klassendiagramm.png: klassendiagramm.uml
	plantuml -Tpng klassendiagramm.uml

klassendiagramm.svg: klassendiagramm.uml
	plantuml -Tsvg klassendiagramm.uml

komponentendiagramm.png: komponentendiagramm.uml
	plantuml -Tpng komponentendiagramm.uml

komponentendiagramm.svg: komponentendiagramm.uml
	plantuml -Tsvg komponentendiagramm.uml

clean:
	rm -f klassendiagramm.pdf klassendiagramm.png klassendiagramm.svg komponentendiagramm.png komponentendiagramm.svg
