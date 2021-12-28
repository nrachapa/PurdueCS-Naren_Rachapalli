CFLAGS = -Wall -Werror -std=gnu99 -g
CC = gcc

BINFILES=test_input.bin
CFILES=parity.c

program: $(BINFILES) $(CFILES)
	$(CC) $(CFILES) $(CFLAGS) -o parity

.PHONY: clean

run: $(BINFILES) $(CFILES)
	./parity $(BINFILES) test_output.bin

clean:
	rm -f parity
	rm -f test_output.bin