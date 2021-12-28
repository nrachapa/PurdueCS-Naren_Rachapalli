#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "linked_list.h"

void print_llist(struct llist *top) {
  if (top == NULL) return;
  struct llist *temp = top;
  int t = 0;
  if (temp->recon) t = 1;
  printf("%s\tlevel: %d\tt/f: %d\n", temp->name, temp->level, t);
  print_llist(temp->next);
}

void free_all(struct llist *top) {
    struct llist *current = top;
    struct llist *temp = NULL;
    while (current) {
        temp = current->next;
        free(current->name);
        current->name = NULL;
        free(current);
        current = temp;
    }
    top = NULL;
    return;
}