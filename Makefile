JC = javac

SRCDIR = ./src
OUTDIR = ./build

JFLAGS = -d $(OUTDIR)/ -cp $(SRCDIR)/
SRCS = $(wildcard $(SRCDIR)/*.java)
CLS = $(SRCS:$(SRCDIR)/%.java=$(OUTDIR)/%.class)

.PHONY: all clean

all : $(CLS)

$(CLS) : $(OUTDIR)/%.class: $(SRCDIR)/%.java
	$(JC) $(JFLAGS) $<

clean :
	rm $(OUTDIR)/*.class
