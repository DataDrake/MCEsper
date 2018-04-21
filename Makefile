NAME    = mcesper
VERSION = 1.0

PREFIX  ?= /usr/local
DATADIR ?= $(PREFIX)/share/$(NAME)

target/$(NAME)-$(VERSION)-jar-with-dependencies.jar:
	mvn clean compile assembly:single

all: target/$(NAME)-$(VERSION)-SNAPSHOT-jar-with-dependencies.jar

install: target/$(NAME)-$(VERSION)-SNAPSHOT-jar-with-dependencies.jar
	install -d $(DESTDIR)$(PREFIX)/bin
	printf "#!/bin/sh\njava -jar %s\n" $(DATADIR)/$(NAME).jar > $(DESTDIR)$(PREFIX)/bin/$(NAME)
	chmod 00755 $(DESTDIR)$(PREFIX)/bin/$(NAME)
	install -Dm 00644 target/$(NAME)-$(VERSION)-SNAPSHOT-jar-with-dependencies.jar $(DESTDIR)$(DATADIR)/$(NAME).jar

clean:
	mvn clean

.PHONY: all

