PARAMS=--toc -N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V linkcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

Systemspezifikation.pdf: Systemspezifikation.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) Systemspezifikation.md -o Systemspezifikation.pdf
