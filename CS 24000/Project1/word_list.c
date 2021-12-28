#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "word_list.h"


struct Word_List *create_node(char *word){
  struct Word_List *node = (struct Word_List *) malloc(sizeof(struct Word_List *));
  format_string(word);
  node->word = (char *) malloc(sizeof(char *));
  strcpy(node->word, word);
  node->next = NULL;
  node->freq = 1;
  return node;
}


int freq_count(struct Word_List *dict, char *word) {
  if (dict == NULL || dict->word == NULL) return 0;
  if (!check_string(word)) return 0;
  format_string(word);
  struct Word_List *trav = dict;
  while (trav) {
    if (strcmp(trav->word, word) == 0) {
      return trav->freq;
    }
    trav = trav->next;
  }
  return 0;
}

void free_word_list(struct Word_List *dict) {
    struct Word_List *current = dict;
    struct Word_List *temp = NULL;
    while (current) {
        temp = current->next;
        if (current->word != NULL) free(current->word);
        current->word = NULL;
        free(current);
        current = temp;
    }
    dict = NULL;
    return;
}

bool check_string(char *word) {
  for (size_t i = 0; i < strlen(word); i++) {
    if (!isalnum(word[i])) return false;
  }
  return true;
}

int word_count(struct Word_List *dict) {
  if (dict == NULL) return 0;
  if (dict->next == NULL && check_string(dict->word)) return 1;
  int count = 1;
  struct Word_List *trav = dict->next;
  while (trav) {
    if (check_string(trav->word)) {
      count ++;
    } 
    trav = trav->next;
  }
  return count;
}

void format_string(char *word) {
  if (word == NULL) return; 
  char temp = toupper(word[0]);
  word[0] = temp;
  for (size_t i = 1; i < strlen(word); i++) {
    temp = tolower(word[i]);
    word[i] = temp;
  }
  return;
}

void print_dict(struct Word_List *dict) {
  if (dict == NULL || dict->word == NULL) { 
    printf("end\n");
    return;
  } 
  struct Word_List *temp = dict;
  printf("-> Word: %s\n", temp->word);
  print_dict(temp->next);
}
