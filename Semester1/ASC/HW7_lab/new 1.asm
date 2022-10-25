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
    s  dw 456, 234, 345    
    l  equ ($-s)/2 ; the length
    d times l db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, l ; ecx contains the length in order to jump if we went through all numbers from s
        mov esi, s ; the address of a
        mov edi, d ; the address of d
        cld ; from right to left
        mov ebx, 10  ; to divide each number by 10
        jecxz Sfarsit
        Repeta:
                lodsw  ; ax = element of array
                mov dx, 0
                div bx ; div by 10
                mov ax, dx ; here we have the rest which is equal to the last digit when dividing by 10
                stosb ; we put a byte from al
        loop Repeta
        Sfarsit:
       
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
