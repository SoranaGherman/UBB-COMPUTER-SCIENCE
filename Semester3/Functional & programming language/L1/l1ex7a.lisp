(defun removeN(l c n)
    (cond
        ((null l) nil)
        ((= c n) (removeN (cdr l) (+ 1 c) n))
        (T (cons (car l) (removeN (cdr l) (+ 1 c) n)))
    )
)

(print (removeN '(1 2 3 4 5 6) 1 4))


(defun myAppend(l1 l2)
    (cond
        ((null l1) l2)
        (T (cons (car l1) (myAppend (cdr l1) l2)))
    )
)

(defun transformL(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (myAppend (transformL (car l)) (transformL (cdr l))))
        (T (cons (car l) (transformL (cdr l))))
    )
)


(defun removeElem(l e)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (removeElem (car l) e) (removeElem (cdr l) e)))
        ((equal (car l) e) (removeElem (cdr l) e))
        (T (cons (car l) (removeElem (cdr l) e)))
    )
)

(defun mySet(l)
    (cond
        ((null l) nil)
        (T (cons (car l) (mySet (removeElem (cdr l) (car l)))))
    )
)

(defun mainS(l)
    (mySet(transformL l))
)

(print (mainS '(1 (2 (1 3 (2 4) 3) 1) (1 4))))


(defun checkElem(l e)
    (cond
        ((null l) T)
        ((equal (car l) e) nil)
        (T (checkElem (cdr l) e))
    )
)

(defun checkList(l)
    (cond
        ((null l) T)
        ((not (checkElem (cdr l) (car l)) ) nil)
        (T (checkList (cdr l)))
    )
)

(print (checkList '(1 2 3 4 1)))
