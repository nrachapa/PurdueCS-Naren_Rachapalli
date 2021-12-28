#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "remove_redundant.h"

int main(int argc, char **argv)
{
    Student_Record* head = NULL;
    
    char name_buff[100]; 
    char* input = argv[1];
    int input_len = strlen(input);
    int inc = 0;
    char pair[100]; /* We can assume total input of each pair will not exceed 99 length */
        
    /* Scan in the input and build the list */
    while (sscanf(input + inc, "(%[^)]s)", pair) == 1) {
        
        int num;
        inc += strlen(pair) + 3;
        
        sscanf(pair, "%d, %s", &num, name_buff);
        char* name = malloc(strlen(name_buff) + 1);
        strcpy(name, name_buff);
        Student_Record* new_student = malloc(sizeof(Student_Record));
        new_student->next = NULL;
        new_student->grade = num;
        new_student->name = name;

        if (!head) head = new_student;
        else {
            Student_Record* current = head;

            while (current->next) current = current->next;

            current->next = new_student;
        } 
            
        if (inc > input_len) break;
    }
    
    /* Your function called here */
    Student_Record* trimmed_student_ls = remove_redundant(head);
    
    /* The list is then printed */
    Student_Record* cur_student = trimmed_student_ls;
    while (cur_student) {
		printf("%s ", cur_student->name);
        cur_student = cur_student->next;
    }
    printf("\n");
    
    return 0;
}

