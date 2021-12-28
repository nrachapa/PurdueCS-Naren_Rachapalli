#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "remove_redundant.h"

Student_Record *remove_redundant(Student_Record *head) {
    Student_Record *current, *temp, *prev, *traverse;
    current = traverse = head;
    traverse = traverse->next;
    while (current) {
        while (traverse != NULL && (strcmp(current->name, traverse->name) != 0)) {
            prev = traverse;
            traverse = traverse->next;
        }
        if (traverse == NULL) {
            current = current->next;
            if (current)
            {
                traverse = current->next;
            }
        } else if (strcmp(current->name, traverse->name) == 0) {
            prev->next = traverse->next;
            temp = traverse;
            traverse = traverse->next;
            free(temp);
        }
    }
    return head;
}