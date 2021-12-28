CC = gcc
CFLAGS = -Wall -Werror -std=gnu99 -g

all: sort_sum remove_redundant

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

sort_sum: sort_sum.o sort_sum.h main1.o
	$(CC) $(CFLAGS) -o sort_sum main1.o sort_sum.o

remove_redundant: remove_redundant.o remove_redundant.h main2.o
	$(CC) $(CFLAGS) -o remove_redundant main2.o remove_redundant.o

.PHONY: clean

clean:
	rm -f sort_sum remove_redundant *.o