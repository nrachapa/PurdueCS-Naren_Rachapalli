CFLAGS = -Wall -Werror -std=gnu99 -g
CC = gcc

HEADERS=homework.h
CFILES=main.c iterator.c

program: $(HEADERS) $(CFILES)
	$(CC) $(CFILES) $(CFLAGS) -o program

.PHONY: clean

clean:
	rm -f program