#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "deallocate_list.h"

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
        
        /* Allocate and copy the name */
        char* name = malloc(strlen(name_buff) + 1);
        strcpy(name, name_buff);
        
        /* Allocate the student */
        Student_Record* new_student = malloc(sizeof(Student_Record));
        new_student->next = NULL;
        new_student->grade = num;
        new_student->name = name;

        /* Append it to the end of the list */
        if (!head) head = new_student;
        else {
            Student_Record* current = head;

            while (current->next) current = current->next;

            current->next = new_student;
        } 
            
        if (inc > input_len) break;
    }
    
    /* Your function called here */
    int result = deallocate_list(head);
    
    printf("Deallocate list returned with value: %d\n", result); 
    
    return 0;
}