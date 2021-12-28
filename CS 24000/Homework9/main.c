#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "homework.h"

#define ACTION_ERROR_MSG "Undefined function"
#define FILE_ERROR_MSG "File open failure"

int min_grade = UNDEFINED;
int max_grade = UNDEFINED;

Student_Record *create_node(char *, int);

// 4 functions processing a node.
void printing(Student_Record *);
void find_max(Student_Record *);
void find_min(Student_Record *);
void reset(Student_Record *);

int main(int argc, const char *argv[])
{
    // Ensures that 2 command-line arguments are supplied.
    if (argc < 3)
    {
        fprintf(stderr, "usage: %s <filename> <action>\n", argv[0]);
        return 1;
    }

    // init file
    FILE *fp;
    fp = fopen(argv[1], "r");
    if (fp == NULL)
    {
        fprintf(stderr, "%s\n", FILE_ERROR_MSG);
        return 0;
    }

    //Convert txt file to Student_Record linked list
    Student_Record *gradebook;
    char line[200];
    if (fgets(line, sizeof(line), fp) == NULL)
    {
        fclose(fp);
        return 0;
    }
    else
    {
        char temp_name[20];
        int temp_grade;
        sscanf(line, "%s %d\n", temp_name, &temp_grade);
        Student_Record *node = create_node(temp_name, temp_grade);
        gradebook = node;
        while (fgets(line, sizeof(line), fp))
        {
            char temp_name[20];
            int temp_grade;
            sscanf(line, "%s %d\n", temp_name, &temp_grade);
            Student_Record *node = create_node(temp_name, temp_grade);
            node->next = gradebook;
            gradebook = node;
        } 
    }
    fclose(fp);

    // Action to Iterator
    if (strcmp(argv[2], "printing") == 0)
    {
        Iterator(gradebook, printing);
    }
    else if (strcmp(argv[2], "find_max") == 0)
    {
        Iterator(gradebook, find_max);
    }
    else if (strcmp(argv[2], "find_min") == 0)
    {
        Iterator(gradebook, find_min);
    }
    else if (strcmp(argv[2], "reset") == 0)
    {
        Iterator(gradebook, reset);
    }
    else
    {
        fprintf(stderr, "%s\n", ACTION_ERROR_MSG);
        return 0;
    }

    //Free list
    Student_Record *current = gradebook;
    Student_Record *temp = NULL;
    while (current)
    {
        temp = current->next;
        free(current->name);
        current->name = NULL;
        free(current);
        current = temp;
    }
    gradebook = NULL;
    return 0;
}

void printing(Student_Record *temp)
{
    if (temp != NULL)
    {
        printf("%s %d\n", temp->name, temp->grade);
    }
}
void find_max(Student_Record *temp)
{
    if (temp == NULL) return;
    if ((max_grade == UNDEFINED) || (temp->grade > max_grade))
    {
        max_grade = temp->grade;
    }
}
void find_min(Student_Record *temp)
{
    if (temp == NULL) return;
    if ((max_grade == UNDEFINED) || (temp->grade < max_grade))
    {
        min_grade = temp->grade;
    }
}
void reset(Student_Record *temp)
{
    if (temp != NULL)
    {
        temp->grade = 0;
    }
}

Student_Record *create_node(char *name, int grade) {
    Student_Record *temp = (Student_Record *) malloc(sizeof(Student_Record));
    temp->name = (char *) malloc(sizeof(name)); 
    strcpy(temp->name, name);
    temp->grade = grade;
    temp->next = NULL;
    return temp;
}