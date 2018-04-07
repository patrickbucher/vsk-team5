PARAMS=-N --toc --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

Scrum.pdf: Scrum.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) Scrum.md -o Scrum.pdf
