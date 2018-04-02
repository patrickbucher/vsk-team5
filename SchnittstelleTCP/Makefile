PARAMS=-N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

SchnittstelleTCP.pdf: SchnittstelleTCP.md Kommunikation.png classes.png
	pandoc -s $(PARAMS) $(FONTS) $(DATE) SchnittstelleTCP.md -o SchnittstelleTCP.pdf

Kommunikation.png: Kommunikation.dot
	dot Kommunikation.dot -Tpng -o Kommunikation.png

classes.png: classes.uml
	plantuml classes.uml
