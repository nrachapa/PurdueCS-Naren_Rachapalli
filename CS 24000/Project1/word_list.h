#ifndef WORD_LIST_H
#define WORD_LIST_H
#include <stdbool.h>


typedef struct Word_List {
  char *word;
  int freq;
  struct Word_List *next;
} dict;

struct Word_List *create_node(char *word);
int freq_count(struct Word_List *dict, char *word);
void free_word_list(struct Word_List *dict);
bool check_string(char *);
int word_count(struct Word_List *dict);
void format_string(char *);
void print_dict(struct Word_List *dict);

#endif