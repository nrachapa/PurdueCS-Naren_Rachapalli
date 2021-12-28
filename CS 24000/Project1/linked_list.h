#ifndef LINKED_LIST_H
#define LINKED_LIST_H
#include <stdbool.h>

typedef struct llist {
  char *name;
  int level;
  bool recon;
  struct llist *next;
} NODE;

void print_llist(struct llist *);
void free_all(struct llist *top);


#endif