(defun checkExistence(e l)
    (cond
        ((null l) T)
        ((= (car l) e) nil)
        (T (checkExistence e (cdr l)))
    )
)

(defun dif(l1 l2)
    (cond 
        ((null l1) nil)
        ((checkExistence (car l1) l2) (cons (car l1) (dif (cdr l1) l2)))
        (T (dif (cdr l1) l2))
    )
)

(print (dif '(1 2 3 4) '(2 4)))


(defun myAppend (l p)
  (cond
    ((null l) p)
    (t (cons (car l) (myAppend (cdr l) p)))
  )
)

(defun myReverse(l)
  (cond
    ((null l) nil)
    ((listp (car l)) (myAppend (myReverse (cdr l)) (list (myReverse (car l)))))
    (t (myAppend (myReverse (cdr l)) (list (car l))))
  )
)

(defun myAppend (l p)
  (cond
    ((null l) p)
    (t (cons (car l) (myAppend (cdr l) p)))
  )
)

(defun oddNrOfElems(l c)
  (cond
    ((and (null l) (equal 1 (mod c 2))) t)
    ((and (null l) (equal 0 (mod c 2))) nil)
    (t (oddNrOfElems (cdr l) (+ 1 c)))
  )
)

(defun checkOdd(l)
  (oddNrOfElems l 0)
)

(defun firstElem(l f)
  (cond
    ((null l) nil)
    ((listp (car l)) (myAppend (firstElem (car l) 0) (firstElem (cdr l) f)))
    ((and (checkOdd l) (= f 0)) (cons (car l) (firstElem (cdr l) 1)))
    (t (firstElem(cdr l) 1))
  )
)

(print (firstElem '((1 2 (3 (4 5) (6 7)) 8 (9 10 11))) 0))


(defun sumS(l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ (car l) (sumS (cdr l))))
        (T (sumS (cdr l))) 
    )
)

(print (sumS '(1 2 (3 4 5(A B (C)))4) ))

