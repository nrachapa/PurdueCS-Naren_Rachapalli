@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ printx.s - converts a bit string into its representation in hexadecimal notation.
@            Omits leading zero hex digits.
@
@ r0 - bit string to be converted
@ r1 - indicates case to use for hex letters, 0 = lower case, 1 = upper case
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

.section .text
.global	printx 
	
printx:
  push {r4-r6,fp,lr}	@ prepare for a clean return
  cmp r0, #15
  bge two
  mov r4, #1
  b end_two 
two:
  mov r4, #2
end_two:
  lsl r0, r0, #24
loop:
  lsr r5, r0, #28
  lsl r0, r0, #4
  add r5, r5, #48
  cmp r5, #58
  blt end_over
over:
  add r5, r5, #7
  cmp r1, #1
  beq end_over
lower:
  add r5, r5, #32
end_over:
  mov r6, r0
  mov r0, r5
  bl putchar
  mov r0, r6
  sub r4, r4, #1
  cmp r4, #0
  beq done
  b loop
done:
  pop {r4-r6,fp,pc}	@ return to caller
