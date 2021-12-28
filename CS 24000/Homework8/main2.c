#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <time.h> 
#include "deallocate_tree.h"

#define MAXIDLEN 100

/*
	construct_node (int value, char name[])
    
   	Consturct a new node by allocating memory and put value and name.
    It returns address of new constructed node		
*/
struct Tree_Node * construct_node(int value, char name[])
{
        struct Tree_Node * node = (struct Tree_Node *) malloc (sizeof(struct Tree_Node));
        
        node->value = value;
    
        node->name  = (char *) malloc (1 + strlen(name));
        strcpy(node->name, name);
        
        node->left  = NULL;
        node->right = NULL;   
 	   
    	return node;
}


/* main function */
int main(int argc, const char *argv[]) 
{
    int value = 0;
    char name[MAXIDLEN] = "\0";
	struct Tree_Node * head = NULL;
    
    /*
        The rand() requires arbitrary value as a random seed. 
    	We use time seconds for that.
    */
    srand(time(NULL));
    
    /* Scan in the input and build the tree */
    while (scanf("%d %s", &value, name) == 2)
    {    
        /*
        	This time, we use rand() to locate new added node in the tree.
            To find an empty slot, we do travel the tree by throwing a dice (rand()).
            When we have an odd number, we search the right child sub-tree, otherwise, the left.
        	Whenever we find an empty slot, we stop searching and put the new node. 
        */
        struct Tree_Node * node = construct_node(value, name);
        if (NULL == head)
        {
            head = node;
        	continue;
        }
        
        /*
        	We start a tree traversal from the head node.
        */
        struct Tree_Node * walk = head;
        do
        {
            /*
            	The rand() will give an random integer.
                "%2" will bring us either 0 or 1.
            */
            switch (rand()%2) 
            {
                // Visit left sub-tree
                case 0:
                    if(NULL == walk->left)  walk->left  = node;
                    walk = walk->left;
                    break;

                // Visit right sub-tree
                case 1:
                    if(NULL == walk->right) walk->right = node;
                    walk = walk->right;
                    break;
                    
            }// end of the switch
        }while (node != walk); // end of the do-while
        
    }// end of the while
    
    int result = deallocate_tree(head);
    printf("Deallocate tree returned with value: %d\n", result);
    
    
    return 0;
}