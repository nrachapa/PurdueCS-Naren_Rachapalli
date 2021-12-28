#include <stdio.h>
#include <stdlib.h>
#include "sort_sum.h"

void remove_last_element(struct Sum *);
void swap_sums_nodes(struct Sum *, struct Sum *);
void descending_sort(struct Sum *);
struct Sum *sort_sum(Linked_Pair *head) {
    struct Sum *unordered_sums = (struct Sum*) malloc(sizeof(struct Sum));
    struct Sum *ordered_sums = unordered_sums;
    struct Sum *sol = ordered_sums;
    Linked_Pair *traverse = head; 
    
    while(traverse) {
        int val = traverse->num_1 + traverse->num_2;
        unordered_sums->sum = val;
        unordered_sums->next = (struct Sum*) malloc(sizeof(struct Sum));
        unordered_sums = unordered_sums->next;
        traverse = traverse->next;
    }

    descending_sort(ordered_sums);
    remove_last_element(sol);

    return sol;
}
void remove_last_element(struct Sum *head) {
    struct Sum *traverse = head;
    if (traverse->next == NULL) {
        free(traverse);
    }
    while (traverse->next->next) {
        traverse =traverse->next;
    }
    free(traverse->next);
    traverse->next = NULL;
}
void swap_sums_nodes(struct Sum *ptr1, struct Sum *ptr2) {
    int val = ptr1->sum;
    ptr1->sum = ptr2->sum;
    ptr2->sum = val;
}
void descending_sort(struct Sum *head) {
    struct Sum *traverse;
    struct Sum *max;

    while (head->next) {
        max = head;
        traverse = head->next;
        while (traverse) {
            if (max->sum < traverse->sum) {
                max = traverse;
            }
            traverse = traverse->next;
        }
        swap_sums_nodes(head, max);
        head = head->next;
    }
}
