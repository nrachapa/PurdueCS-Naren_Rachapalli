#ifndef HOMEWORK_H
#define HOMEWORK_H

#define UNDEFINED -1

extern int min_grade;
extern int max_grade;

typedef struct Student {
    int grade;
    char *name;
    struct Student *next;
} Student_Record;

// Traverses through the linked list, and 
// invokes function `process_node` on each node.
void Iterator(Student_Record *head, void (*process_node)(Student_Record *));


#endif  // HOMEWORK_H