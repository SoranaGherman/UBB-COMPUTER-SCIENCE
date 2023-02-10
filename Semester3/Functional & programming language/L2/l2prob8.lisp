(defun countN(l k cnt)
    (cond
        ((and (= k cnt) (atom l)) 1)
        ((atom l) 0)
        (T (apply '+ (mapcar #' (lambda (l) (countN l k (+ 1 cnt))) l)))
    )
)


(print (countN '(a (b (c)) (d) (e (f))) 1 -1))