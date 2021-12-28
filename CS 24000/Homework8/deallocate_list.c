#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "deallocate_list.h"

int deallocate_list(Student_Record *head) {
    if (head == NULL) return -1;
    Student_Record *current, *traverse;
    current = head->next;
    while(current) {
        traverse = current;
        current = current->next;
        free(traverse);
    }
    head = NULL;
    return 0;
}