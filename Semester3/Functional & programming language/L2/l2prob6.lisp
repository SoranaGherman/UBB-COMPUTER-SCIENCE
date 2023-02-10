(defun myMax(a b)
  (cond
    ((and (not (numberp a)) (not (numberp b))) nil)
    ((not (numberp a)) b)
    ((not (numberp b)) a)
    ((> a b) a)
    (t b)
  )
)

(defun maxList(l)
  (cond
    ((null l) nil)
    (t (myMax (car l) (maxList (cdr l))))
  )
)

(defun maxForList(l)
  (cond
    ((numberp l) l)
    ((atom l) nil)
    (t (apply #'maxList (list (mapcar #'maxForList l))))
  )
)

(print (maxForList '((1 2 3) (3 (1 (5))))))