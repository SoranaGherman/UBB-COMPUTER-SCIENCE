(defun myAppend (l p)
  (cond
    ((null l) p)
    (t (cons (car l) (myAppend (cdr l) p)))
  )
)


(defun myAppendList(l)
  (cond
    ((null l) nil)
    (t (myAppend (car l) (myAppendList (cdr l))))
  )
)


(defun transformL(l)
    (cond 
        ((atom l) (list l))
        (T (apply #'myAppendList(list (mapcar #'transformL l))))
    )
)


(print (transformL '(((A B) C) (D E))))
