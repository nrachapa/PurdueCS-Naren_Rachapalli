@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ u_divide_by_10 - function that performs integer division by 10
@
@ r0 - integer on which to perform unsigned division by 10
@
@ Integer division is a somewhat frequently used function that is
@ also very slow when implemented directly. For division by a
@ constant, research developed a combination of multiplication by a
@ magic number and a logical right shift that produces the correct
@ result much more rapidly. Because of this algorithm, the ARM ISA
@ of our VM has no integer divide instruction. Either divide quickly
@ by a constant, or using the direct algorithm for a runtime value.
@ When high-level language programs perform integer division by a
@ constant, compiler writers use this algorithm to translate the HLL
@ into a version of the following code, adjusted for the specific
@ constant integer.
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

u_divide_by_10:
    ldr r1, .Lu_magic_number_10 @ r1 ← magic_number
    umull r1, r2, r1, r0        @ unsigned integer multiply
                                @ r1 ← Lower32Bits(r1*r0) and
                                @ r2 ← Upper32Bits(r1*r0)
    lsr r0, r2, #3              @ r0 ← r2 >> 3
    b next                      @ leave function

.align 4
.Lu_magic_number_10:
    .word 0xcccccccd            @ magic number for divisor = 10


@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ printd - takes a 2's complement integer and outputs its
@          sign magnitude decimal representation
@          as a series of ASCII characters. 
@ r0 - a 2's complement integer
@
@ Local variables suggestions
@ r4 - negative flag, uses another "magic number" to assist to ASCII
@ r5 - various temporary values
@ r6 - count of symbols pushed on the stack
@ r7 - temporary for 10 and 10*Quotient
@ r8 - remainder, a digit of %d; R = Current_Magnitude - 10*Quotient 
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@<your assembler directives and printd function code here>
.global printd

printd: 
    push {r4-r8,fp,lr}	@ prepare for a clean return
    mov r5, r0
    lsr r5, r5, #31
    cmp r5, #1
    beq skip
    mov r5, r0
    mov r6, #0
    b divide

skip:
    mov r4, #0xFFFFFFFF
    sub r0, r4, r0
    add r0, r0, #1
    mov r4, #21
    mov r5, r0
    mov r6, #0

divide:
    b u_divide_by_10

next:
    mov r7, #10
    mul r7, r0, r7
    sub r8, r5, r7
    add r8, r8, #48
    mov r5, r0
    push {r8}
    add r6, r6, #1
    cmp r5, #0
    bne divide
    cmp r4, #21
    bne end_printd
    mov r4, #45
    push {r4}
    add r6, r6, #1

end_printd:
    pop {r0}
    sub r6, r6, #1
    bl putchar
    cmp r6, #0
    bne end_printd
    pop {r4-r8,fp,pc}
