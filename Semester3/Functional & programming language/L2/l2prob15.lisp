(defun myAppend(l1 l2)
    (cond
        ((and (null l1) (listp l2)) l2)
        ((and (null l1)) list(l2))
        (T (cons (car l1) (myAppend (cdr l1) l2)))
    )
)

(defun myReverse(l)
    (cond
        ((null l) nil)
        (T (myAppend(myReverse(cdr l)) (list(car l))))
    )
)

(defun ReverseAll(l)
    (cond
        ((null l) nil)
        ((atom l) l)
        (T (mapcar #'ReverseAll(myReverse l)))
    )
)


(print (ReverseAll '(1 2 3 (4 5 (6 7)) (A B))))

