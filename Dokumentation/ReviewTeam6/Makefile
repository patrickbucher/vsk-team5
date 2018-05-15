PARAMS=--toc -N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V linkcolor=blue
FONTS=-V mainfont="Droid Serif" -V sansfont="Droid Sans" -V monofont="Droid Sans Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

ReviewDokumentationGruppe6.pdf: ReviewDokumentationGruppe6.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) ReviewDokumentationGruppe6.md -o ReviewDokumentationGruppe6.pdf
