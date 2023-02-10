(defun checkMember(l e)
    (cond
        ((null l) nil)
        ((listp (car l)) (or (checkMember (car l) e) (checkMember (cdr l) e)))
        ((equal (car l) e) T)
        (T (checkMember (cdr l) e))
    )
)

(print (checkMember '(1 2 (3 (5))) 6))

(defun removeE(l e)
    (cond
        ((null l) nil)
        ((= (car l) e) (removeE (cdr l) e))
        (T (cons (car l) (removeE (cdr l) e)))
    )
)

(defun transformLS(l)
    (cond
        ((null l) nil)
        (T (cons (car l) (transformLS (removeE (cdr l) (car l)))))
    )
)

(print (transformLS '(1 2 4 1 5 1 2 4 5 6)))
