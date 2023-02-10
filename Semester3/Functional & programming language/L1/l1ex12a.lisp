(defun vectorProd(l1 l2)
    (cond
        ((null l1)  0)
        (T (+(* (car l1) (car l2)) (vectorProd (cdr l1) (cdr l2))))
    )
)

(print (vectorProd '(1 2 3) '(4 -1 2)))


(defun max2(a b)
    (cond
        ((and (not (numberp a)) (not(numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((< a b) b)
        ((< b a) a)
    )
)

(defun maxL(l)
    (cond
        ((and (null (cdr l)) (atom (car l))) (car l))
        ((listp (car l)) (max2 (maxL (car l)) (maxL (cdr l))))
        (T (max2 (car l) (maxL (cdr l))))
    )
)

(print(maxL '(1 2 (3 (5 1)) 3)))

(defun evenLength(l)
    (cond
        ((null l) T)
        ((null (cdr l)) nil)
        (T (evenLength (cddr l)))
    )
)

(print(evenLength '(1 2 (3 (5 1)))))