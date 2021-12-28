CFLAGS = -Wall -Werror -std=gnu99 -g
CC = gcc
HEADERS=linked_list.h word_list.h
CFILES=html_parser.c linked_list.c word_list.c
target=html_parser

all: $(HEADERS) $(CFILES)
	$(CC) $(CFILES) $(CFLAGS) -o $(target) 
index: ; ./$(target) index.html output.txt
test: ; ./$(target) -c -f SeCtiOn test.html output.txt
clean:
	rm -f $(target) && rm -f output.txt