(defun sum(l)
    (cond
        ((numberp l) l)
        ((atom l) 0)
        (T (apply '+ (mapcar #'sum l)))
    )
)

(print (sum '(1 2 3 (4 5 (6 (7))))))