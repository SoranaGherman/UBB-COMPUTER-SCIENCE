(defun max2(a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((< a b) b)
        (T a)
    )
)

(defun findMax(l)
  (cond
    ((null l) nil)
    (t (max2 (car l) (findMax (cdr l))))
  )
)

(defun depthL(l counter)
  (cond
    ((atom l) counter)
    (t (apply #'findMax (list (mapcar #' (lambda (a) (depthL a (+ 1 counter))) l))))
  )
)

(print (depthL '(1 ( 2 ( 4 (5))) (3)) -1))