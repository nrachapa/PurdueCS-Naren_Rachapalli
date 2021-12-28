@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ main.s for function calling conventions lab
@ 
@ Matches the function of main.c for function calling conventions lab.
@ A number of data items are declared and a memory space reservation made.
@ Only registers r0 and r1 are used.
@ Because ARM programming convention assumes that these registers are
@ for argument passing, they are not pushed to the stack upon function entry.
@ Only the frame pointer and link register contents need be saved to the stack.
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

.section .data

@ your data declarations here
prompt1: .asciz "Enter a string: "
prompt2: .asciz "Enter the start index: "
prompt3: .asciz "Enter the end index: "
output: .asciz "The substring of the given string is '%s'\n"
str_format: .asciz "%s"
int_format: .asciz "%d"
input_str: .space 100
start_idx: .word 0
end_idx: .word 0

.section .text
.global main

main:
    push {fp,lr}
    
    @ String Prompt
    ldr r0, =prompt1
    bl printf
    
    @ Scan String
    ldr r0, =str_format
    ldr r1, =input_str
    bl scanf

    @ Start Index Prompt
    ldr r0, =prompt2
    bl printf

    @ Scan Start Index
    ldr r0, =int_format
    ldr r1, =start_idx
    bl scanf

    @ End Index Prompt
    ldr r0, =prompt3
    bl printf

    @ Scan End Index
    ldr r0, =int_format
    ldr r1, =end_idx
    bl scanf

    @ Saving Outputs and Dereferencing
    ldr r0, =input_str
    ldr r1, =start_idx
    ldr r1, [r1]
    ldr r2, =end_idx
    ldr r2, [r2]

    @ Branch into substring function and print result
    bl sub_string
    mov r1, r0
    ldr r0, =output
    bl printf

                        
@ your assembly language instructions here
@ call no functions other than printf, scanf, and sub_string
   

    pop {fp,pc}
