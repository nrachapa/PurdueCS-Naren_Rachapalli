#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sort_sum.h"

int main(int argc, char **argv)
{
    Linked_Pair* head = NULL;
    char* input = argv[1];
    int input_len = strlen(input);
    int inc = 0;
    char pair[100]; /* We can assume total input of each pair will not exceed 99 length */
    
    /* Scan in the input and build the list */
    while (sscanf(input + inc, "(%[^)]s)", pair) == 1) {
        
        int num_one, num_two;
        inc += strlen(pair) + 3;
        
        sscanf(pair, "%d, %d", &num_one, &num_two);
        
        Linked_Pair* new_pair = malloc(sizeof(Linked_Pair));
        new_pair->next = NULL;
        new_pair->num_1 = num_one;
        new_pair->num_2 = num_two;
        
        if (!head) head = new_pair;
        else {
            Linked_Pair* current = head;
            
            while (current->next) current = current->next;
            
            current->next = new_pair;
        }
        
        if (inc > input_len) break;
    }
    
    /* Your function called here */
    struct Sum* sorted_sum_ls = sort_sum(head);
    
    struct Sum* cur_sum = sorted_sum_ls;
    while (cur_sum) {
		printf("%d ", cur_sum->sum);
        cur_sum = cur_sum->next;
    }
    printf("\n");
    
    return 0;
}



