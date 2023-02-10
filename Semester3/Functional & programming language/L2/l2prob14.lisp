(defun noAtoms(l)
    (cond
        ((atom l) 1)
        (T (apply '+ (mapcar #'noAtoms l)))

    )
)

(print (noAtoms '(1 2 (3 2 A (B (5))))))