(defun myMax (a b)
  (cond
    ((> a b) a)
    (t b)
  )
)


(defun findDepth (l c)
  (cond
    ((null l) c)
    ((listp (car l)) (myMax (findDepth (car l) (+ c 1)) (findDepth (cdr l) c)))
    (t (findDepth (cdr l) c))
  )
)

(print (findDepth '(1 2 (3 (4 (5)) 3 ) 4 ) 1))


(defun insertOk(l e)
    (cond
        ((null l) (list e))
        ((< e (car l)) (cons e l))
        ((= e (car l)) l)
        (T (cons (car l) (insertOk (cdr l) e)))
    )
)

(defun sortL (l)
    (cond
        ((null l) nil)
        (T (insertOk (sortL (cdr l)) (car l)))
    )
)


(print (sortL '(1 2 8 2 1 8 4)))


(defun checkL (e l)
    (cond
        ((null l) nil)
        ((= (car l) e) T)
        (T (checkL e (cdr l)))
    )
)

(defun myIntersection(l1 l2)
    (cond
        ((null l1) nil)
        ((checkL (car l1) l2) (cons (car l1) (myIntersection (cdr l1) l2)))
        (T (myIntersection (cdr l1) l2)) 
    )
)

(print (myIntersection '(1 2 3 4) '(1 4)))