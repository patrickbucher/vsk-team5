PARAMS=--toc --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

Analyse.pdf: Analyse.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) Analyse.md -o Analyse.pdf

clean:
	rm -f Analyse.pdf
