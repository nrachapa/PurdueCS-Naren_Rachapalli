// main.c for function calling conventions lab

#include <stdio.h> 
#include <stdlib.h>
#include <string.h>

int sub_string(char *in_string, int start_index, int end_index);

int main() {
    
// <your code here>
    char in_string[99], *out_string;
    int start_index, end_index; 
    printf("Enter a string: ");
    scanf("%s", in_string);
    fflush(stdin);
    printf("Enter the start index: ");
    scanf("%d", &start_index);
    fflush(stdin);
    printf("Enter the end index: ");
    scanf("%d", &end_index);
    out_string = (char*) sub_string(in_string, start_index, end_index);

    printf("The substring of the given string is '%s'\n", out_string);
}
