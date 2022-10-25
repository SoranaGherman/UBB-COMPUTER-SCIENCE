bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 14
    b db 20
    c db 23
    d db 10
    
;(b+c)+(a+b-d)
; our code starts here
segment code use32 class=code
    start:
            mov AL, [b]
            add AL, [c]   ; b + c
            mov BL, [a]
            add BL, [b]     ; a + b
            sub BL, [d]      ; a+ b - d
            add AL, BL      ;(b+c)+(a+b-d)
            
            
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
