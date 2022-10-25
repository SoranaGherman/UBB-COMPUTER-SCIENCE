bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fread, fwrite, fprintf, fscanf 
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fwrite msvcrt.dll
import fprintf msvcrt.dll
import fscanf msvcrt.dll

;A text file is given. The text file contains numbers (in base 10) separated by spaces. Read the content of the file, determine the maximum number (from the numbers that have been read) and write the result at the end of file.


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "this_file.txt", 0 ; create a file
    access_mode db "a+", 0          ; file access mode
    file_descriptor dd -1           ; holds the file descriptor
    format db "%d", 0
    max_value dd -2000000000        ; holds the maximum of the array
    current_value dd 0              ; the current value of the array to compare to max_value
    space db 13
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4 * 2
        
        mov [file_descriptor], eax
        cmp eax, 0
        je The_End
        
        _loop:
                push dword current_value
                push dword format
                push dword [file_descriptor]
                call [fscanf]
                add esp, 4 * 3
                
                cmp eax, -1
                je clean_up
                
                mov ebx, [current_value]
                cmp ebx, [max_value]
                jg update
                jmp step_over
                
                update:
                        mov [max_value], ebx
                
        
                step_over:
                            jmp _loop
        
        clean_up:
                    push dword [file_descriptor]
                    push dword 1
                    push dword 1
                    push dword space
                    call [fwrite]
                    add esp, 4 * 4
                    
        push dword [max_value]
        push dword format
        push dword [file_descriptor]
        call [fprintf]
        add esp, 4 * 3
 
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        
        The_End:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program