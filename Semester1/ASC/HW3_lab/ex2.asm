bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a db 20
    b db 30
    c db 7
    d dd 100 
    x dq 1000

; our code starts here
;(a+b)/(c-2)-d+2-x
segment code use32 class=code
    start:
        ; ...
        mov BL, [c]
        sub BL, 2    ; BL <- c - 2
        
        mov AL, [a]
        add AL, [b]
        cbw           ; AX <- a + b
        idiv BL       ; AX <- (a + b) / (c - 2)
        
        mov BX, 2
        add BX, AX    ; BX <- (a + b) / (c - 2) + 2
        mov AX, BX
        cwde          ; EAX <- (a+b)/(c-2)-d+2
        cdq
        
        mov EBX, EAX
        mov ECX, EDX
        
        mov EAX, dword [x+0] 
        mov EDX, dword [x+4]
        sub EBX, EAX
        sbb ECX, EDX 
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
