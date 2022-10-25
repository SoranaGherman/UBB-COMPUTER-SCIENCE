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
    s dw 1432h
    l equ $-s
    d times l db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, 4
        mov esi, s
        mov edi, d
        mov bl, 10
        mov dl, 10
        
        lodsw ; AX = 1432
        
        jecxz Sfarsit
        Repeta:
 
                div bl ; AL = catu, ah = ultima cifra
                cmp ah, dl
                jle update
                jmp continua
                
                update:
                        mov dl, ah
                
                continua:
                
        
        loop Repeta
        Sfarsit:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
