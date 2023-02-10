(defun checkAtom(l a)
    (cond
        ((and (atom l) (equal l a)) 1)
        ((atom l) 0)
        (T (apply '+ (mapcar #'(lambda (l) (checkAtom l a)) l)))
    )
)

(defun mainA(l a)
    (cond
        ((equal (checkAtom l a) 0) nil)
        (T T)
    )
)

(print (mainA '(1 (2 3) 4) 2))