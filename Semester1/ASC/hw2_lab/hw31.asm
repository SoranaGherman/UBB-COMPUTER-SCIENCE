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
    a db 10
    b db 20
    c db 5
    d dw 70

;[d-(a+b)*2]/c
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL, [a]
        add AL, [b]  ; a + b
        mov BL, 2
        mul BL       ; (a+b)/2
        mov BX, [d]
        sub BX, AX    ; d - (a+b)/2
        mov AX, BX
        mov DL, [c]  
        div DL        ; [d-(a+b)*2]/c
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
