bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit , printf, scanf            ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll  ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll            ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)

;Read three numbers a, m and n (a: word, 0 <= m, n <= 15, m > n) from the keyboard. 
;Isolate the bits m -> n of a and display the integer represented by those bits in base 16

segment data use32 class=data
    ; ...
    a dw 0
    m db 0
    n db 0
    scana dd 0
    scanm dd 0
    scann dd 0
    message1 db "a = ", 0
    message2 db "m = ", 0
    format1 db "%d"
    message3 db "n = ", 0
    format db "The number is %x"

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword message1
        call [printf]
        add esp, 4 * 1
        
        push dword scana
        push dword format1
        call [scanf]
        add esp, 4 * 2
        
        push dword message2
        call [printf]
        add esp, 4 * 1
        
        push dword scanm
        push dword format1
        call [scanf]
        add esp, 4 * 2
        
        push dword message3
        call [printf]
        add esp, 4 * 1
        
        push dword scann
        push dword format1
        call [scanf]
        add esp, 4 * 2
        
        mov ax, [scana]
        mov [a], ax
        
        mov al, [scanm]
        mov [m], al
        
        mov al, [scann]
        mov [n], al
        
        mov ax, [a]
        
        mov bl, [m]
        mov cl, 15
        sub cl, bl
        shl ax, cl ; the bits from pos 0 to n-1 become 0
        
        mov cl, 15
        mov dl, [n]
        sub bl, dl ; BL: m-n
        sub cl, bl ; CL: 15 - (m-n)
        shr ax, cl ; the bits from pos m+1 to 15 become 0
        
        mov edx, 0
        mov dx, ax
        push edx
        push dword format
        call [printf]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
