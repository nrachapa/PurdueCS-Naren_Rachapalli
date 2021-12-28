#ifndef deallocate_tree_H
#define deallocate_tree_H

struct Tree_Node {
  int value; 
  char *name;
  struct Tree_Node *left, *right;
};

int deallocate_tree(struct Tree_Node * tree);

#endif