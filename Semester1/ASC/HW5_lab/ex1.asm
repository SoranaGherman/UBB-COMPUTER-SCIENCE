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
    a db -10
    b dw 20
    c dd 100
    d dq 200

; our code starts here

;(a+a)-(b+b)-(c+d)+(d+d)

segment code use32 class=code
    start:
        ; ...
        mov AL, [a] 
        add AL, [a]  
        cbw          ;  AX <- a + a
        
        mov BX, [b]
        add BX, [b]  ;  BX <- b + b
        sub AX, BX   ; (a+a) - (b+b)
        cwde         ;  EAX <- (a+a) - (b+b)
        cdq
        
        mov EBX, EAX  ;(a+a) - (b+b)
        mov ECX, EDX
        mov EAX, [c]
        cdq
        
        sub ebx, eax  ; eax=  eax+ebx 
        sbb ecx, edx ; edx =  edx+ecx + CF 
        
        mov eax, dword [d+0] 
        mov edx, dword [d+4] 
        
        sub ebx, eax  
        sbb ecx, edx     ;(a+a)-(b+b)-c-d
        
        add ebx, eax     ;(a+a)-(b+b)-(c+d)+d
        adc ecx, edx     
        add ebx, eax
        adc ecx, edx     ;(a+a)-(b+b)-(c+d)+(d+d)
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
