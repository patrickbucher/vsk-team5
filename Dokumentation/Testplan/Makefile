PARAMS=--toc -N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V linkcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

Testplan.pdf: Testplan.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) Testplan.md -o Testplan.pdf
