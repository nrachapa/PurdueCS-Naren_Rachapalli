#include <stdio.h>

#include "homework.h"

void find_min(Student_Record *);
void find_max(Student_Record *);

void Iterator(Student_Record *head, void (*process_node)(Student_Record *)) {
    struct Student *temp = NULL;
    for (temp = head; temp != NULL; temp = temp->next) {
        (*process_node)(temp);
    }
    
    if (process_node == find_min) printf("min=\t%d\n", min_grade);
    if (process_node == find_max) printf("max=\t%d\n", max_grade);
}