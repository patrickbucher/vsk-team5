PARAMS=--toc --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

%.pdf: %.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) $< -o $@ 
