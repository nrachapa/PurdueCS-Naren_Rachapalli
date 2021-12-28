CFLAGS = -Wall -Werror -std=gnu99 -g
CC = gcc

all: deallocate_list deallocate_tree

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

deallocate_list: main1.o deallocate_list.o deallocate_list.h
	$(CC) $(CFLAGS) -o $@ main1.o deallocate_list.o

deallocate_tree: deallocate_tree.c deallocate_tree.h main2.o
	$(CC) $(CFLAGS) -o main2.o -c main2.c
	$(CC) $(CFLAGS) -o deallocate_tree deallocate_tree.c deallocate_tree.h main2.o

clean:
	rm -f *.o deallocate_list deallocate_tree 

