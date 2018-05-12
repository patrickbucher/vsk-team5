PARAMS=--toc -N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V linkcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

TCP-Schnittstelle.pdf: TCP-Schnittstelle.md Kommunikation.png classes.png sequence.png
	pandoc -s $(PARAMS) $(FONTS) $(DATE) TCP-Schnittstelle.md -o TCP-Schnittstelle.pdf

Kommunikation.png: Kommunikation.dot
	dot Kommunikation.dot -Tpng -o Kommunikation.png

sequence.png: sequence.uml
	plantuml sequence.uml

classes.png: classes.uml
	plantuml classes.uml
