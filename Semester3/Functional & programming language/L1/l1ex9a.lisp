(defun mergeL(l1 l2)
    (cond 
        ((and (null l1) (null l2)) nil)
        ((null l1) l2)
        ((null l2) l1)
        ((< (car l1) (car l2)) (cons (car l1) (mergeL (cdr l1) l2)))
        (T (cons (car l2) (mergeL l1 (cdr l2))))
    )
)

(print (mergeL '(1 2 3 5 5 5) '(3 4 5 6)))

 
(defun myAppend (L1 L2)
  (cond
    ((null L1) L2)
    (T (cons (car L1) (myAppend (cdr L1) L2)))
  )
)


(defun replaceList (E R L)
    (cond
        ((null L) ())
        ((listp (car L)) (cons (replaceList E R (car L)) (replaceList E R (cdr L))))
        ((equal E (car L)) (myAppend R (replaceList E R (cdr L))))
        (t (cons (car L) (replaceList E R (cdr L))))
    )
)

 (print (replaceList 2 '(1 2 3) '(1 2 (3 2 (2))) ))


(defun mygcd(a b)
    (cond
        ((not (numberp b)) a)
        ((not (numberp a)) b)
        ((= 0 b) a)
        (T (mygcd b (mod a b)))
    )
)

(defun gcdL(l)
    (cond
        ((null l) nil)
        (T (mygcd (car l) (gcdL (cdr l))))
    )
)

(print (gcdL '(6 9 12)))