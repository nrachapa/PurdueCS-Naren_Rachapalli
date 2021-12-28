@ sub_string.s
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ Takes three arguments and returns a pointer to a string
@ r0 - pointer to first char of a string
@ r1 - index of the starting position of the substring to return
@ r2 - index of the final char to include in the returned substring
@
@ Locals
@ r4 - pointer to the returned substring
@ r5 - pointer to substring start character
@ r6 - pointer to substring end character
@ r7 - value of input string char to copy to output string 
@ out_string - label for created substring in memory
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

.section .data
out_string: .space 100
@ define local data

.section .text
.global sub_string

sub_string:
    push {r4-r7, fp, lr}
    
    add r1, r1, #-1       @ Start Index = Start Index - 1
    add r5, r0, r1        @ pointing to the first character in the substring
    ldr r4, =out_string   @ r4 = output

create_substring:
    ldrb r7, [r5, r1]     @ r7 = first character + start index
    cmp r7, #0            @ check if r7 is null termination character
    beq finish_substring  @ if null termination, branch into finish_substring
    strb r7, [r4]         @ store the r7 sub_string character into r4
    add r1, r1, #1        @ Start Index = Start Index + 1
    add r4, r4, #1        @ pointer moves to the newly added r7 value
    cmp r1, r2            @ check is start index == end index
    bne create_substring  @ if false, loop into create_substring
finish_substring:
    mov r7, #0            @ r7 = '\0'
    strb r7, [r4]         @ store last r7 value to terminate string
    ldr r0, =out_string   @ load the string with a pointer to the beginning of out_string
@ your assembly language instructions here
@ use only the labels create_substring and finish_substring


    pop {r4-r7, fp, pc} @ return to caller
