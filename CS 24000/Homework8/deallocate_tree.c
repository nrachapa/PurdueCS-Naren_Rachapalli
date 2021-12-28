#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "deallocate_tree.h"

int deallocate_tree(struct Tree_Node * tree) {
    if (tree == NULL) return -1;
    deallocate_tree(tree->right);
    deallocate_tree(tree->left);
    free(tree);
    return 0;
}