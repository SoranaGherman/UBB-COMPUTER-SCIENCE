(defun product(l)
    (cond
        ((numberp l) l)
        ((atom l) 1)
        (T (apply '* (mapcar #'product l)))

    )
)

(print (product '(1 (2 3 (4 (2))))))