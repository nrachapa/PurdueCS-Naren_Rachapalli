@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ insertionSort
@   ARM assembly language program.
@   Performs insertion sort on an array of 1 or more elements.
@   Array elements are 32-bit integer representations.
@
@ Arguments are passed to this program as follows. 
@ r0 - contains a pointer to the array of 32-bit integers to be sorted.
@ r1 - contains the number of integers in the array.
@
@ Required register use within this program, in order of appearance:
@ r2 - pointer to the byte after the last (rightmost) array element.
@ r4 - pointer to the first (leftmost) unsorted array element.
@ r5 - copy of the value of the first unsorted array element.
@ r6 - pointer to the sorted element under consideration for re-positioning.
@ r7 - copy of value of sorted element under consideration for re-positioning.
@
@ Required branch target labels in this program:
@ outerLoop , innerLoop , endInnerLoop , and endOuterLoop.
@
@ Program listing style requirements:
@ Place labels on their own line in the program listing.
@ Indent assembly language instruction by 4 spaces.
@ Each assembly language instruction must have an explanatory comment.
@ Begin comments with @ and align successive comment fields vertically.
@
@ Algorithmic requirements:
@ All assembly instructions in the program must have 3 or fewer arguments.
@
@ Because the time cost of a memory access is so large, the total number of
@ ldr and str instructions executed must be minimized. Specifically, insertion
@ of an unsorted element into its sorted position in the array may use only
@ one load register (ldr) instruction and one store register (str) instruction
@ to access that unsorted array element. This can be accomplished by shifting
@ array sorted elements into a new position, rather than exchanging elements.
@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

.section .text

.global insertionSort     @ make symbolic address, insertionSort, known to
                          @ other functions. This makes the instruction with
                          @ the label insertionSort the first instruction of
                          @ a callable function that is called using the
                          @ function name, insertionSort.

insertionSort:            @ here is the first instruction of insertionSort
    push {r4-r7, fp, lr}  @ push copies registers, frame pointer, and link register
                          @ to stack in memory so, later, can restore
                          @ register contents and return to calling function

@ < add your first three assembly language instructions here; comments are a hint>
    lsl r2, r1, #2      @ r2 = number of array elements x 4 = number of bytes
    add r2, r2, r0      @ now r2 points to byte after last unsorted element
    add r4, r0, #4      @ r4 points to the first (leftmost) unsorted element
    b outerLoop
outerLoop:
    cmp r4, r2          @ compare location with the first and last value
    bge endOuterLoop    @ if r4 location surpasses the size of the array break loop
    ldr r5, [r4]        @ A[i] = r5 = first unsorted value
    sub r6, r4, #4      @ j = i - 1
    b innerLoop   
innerLoop:
    cmp r6, r0          @ j >= 0 else end Inner Loop
    blt endInnerLoop
    ldr r7, [r6]        @ r6 points to the previous index, r7 = previous value
    cmp r7, r5          @ A[j] > A[i] else end Inner Loop
    ble endInnerLoop    
    str r7, [r6, #4]    @ A[j + 1] = A[j]
    sub r6, r6, #4      @ j = j - 1
    b innerLoop         @ loop inner loop
endInnerLoop:
    str r5, [r6, #4]    @ A[j + 1] = A[i]
    add r4, r4, #4      @ i = i + 1
    b outerLoop         @ loop outer loop 

@ < add your remaining assembly language instructions and comments here >


endOuterLoop:
    pop {r4-r7, fp, pc} @ restore saved registers and return to caller by
                        @ copying lr from stack into PC for next fetch
