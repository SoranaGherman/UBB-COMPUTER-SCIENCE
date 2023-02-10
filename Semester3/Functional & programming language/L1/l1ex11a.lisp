(defun cmmdc(a b)
    (cond
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((= b 0) a)
        (T (cmmdc b (mod a b) ))
    )
)

(defun cmmmc(a b)
    (cond
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        (T  (/ (* a b) (cmmdc a b)))
    )
)

(defun lcmForList(l)
  (cond
    ((null l) nil)
    ((listp (car l)) (cmmmc (lcmForList (car l)) (lcmForList (cdr l))))
    (t (cmmmc (car l) (lcmForList (cdr l))))
  )
)

(print (lcmForList '(1 (2 3 4))))

(defun max2(a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((< a b) b)
        (T a)
    )
)

(defun maxL(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (max2 (maxL (car l)) (maxL (cdr l))))
        (T (max2 (car l) (maxL (cdr l))))
    )
)

(defun removeMax(l max)
    (cond
        ((null l) nil)
        ((and (atom (car l)) (equal (car l) max)) (removeMax (cdr l) max))
        ((listp (car l)) (cons (removeMax (car l) max) (removeMax (cdr l) max)))
        (T (cons (car l) (removeMax (cdr l) max)))
    )
)

(defun mainM(l)
    (removeMax l (maxL l))
)

(print (mainM '(1 (2 (A C 10 B) 10) 10)))


(defun prod(l)
    (cond
        ((null l) 1)
        ((listp (car l)) (* (prod (car l)) (prod (cdr l))))
        ((and(numberp (car l)) (= 0 (mod (car l) 2))) (* (car l) (prod (cdr l))))
        (T (prod(cdr l)))
    )
)

(print (prod '(1 (2 1 (A 4 )3 2) 5)))


