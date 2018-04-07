PARAMS=-N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

Einrichtung-IntelliJ.pdf: Einrichtung-IntelliJ.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) Einrichtung-IntelliJ.md -o Einrichtung-IntelliJ.pdf
