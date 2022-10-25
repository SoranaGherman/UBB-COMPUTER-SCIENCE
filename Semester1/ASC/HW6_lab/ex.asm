bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)

;Given the word A,obtain the doubleword B as follows:
;the bits 0-3 of B are the same as the bits 1-4 of the result A XOR 0Ah
;the bits 4-11 of B are the same as the bits 7-14 of A
;the bits 12-19 of B have the value 0
;the bits 20-25 of B have the value 1
;the bits 26-31 of C are the same as the bits 3-8 of A complemented

segment data use32 class=data
    ; ...
    a dw 0000000010010001b
    b dd 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ebx, 0 ; we compute the result in ebx
        
        mov cx, [a] ; A = 145
        xor cx, 0Ah ; A XOR 0Ah = 155
        and cx, 0000000000011110b ;we isolate the bits 1-4 of A (26)
        mov ax, cx
        cwde
        
        mov cl, 1
        ror eax, cl
        mov ebx, eax   ;the bits 0-3 of B are the same as the bits 1-4 of the result A XOR 0Ah
        
        mov cx, [a]
        and cx, 00111111110000000b ;we isolate the bits 7-14 of A (26)
        mov ax, cx
        cwde
        
        mov cl, 3
        ror eax, cl
        or ebx, eax   ; B:29
        
        or ebx, 00000011111100000000000000000000b   ;66060317
        
        mov cx, [a]
        not cx    ; each bit 0 becomes 1 and 1 0
        add cx, 1  ; A complemented
        
        and cx, 0000000111111000b ; we isolate the bits 3-8 of A complemented
        mov ax, cx
        cwde
        
        mov cl, 23
        rol eax, cl
        or ebx, eax ; B

    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
