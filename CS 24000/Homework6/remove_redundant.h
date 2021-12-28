#ifndef REMOVE_REDUNDANT_H
#define REMOVE_REDUNDANT_H
typedef struct Student {
  int grade; 
  char *name;
  struct Student *next;
}  Student_Record;

Student_Record *remove_redundant(Student_Record *);
#endif