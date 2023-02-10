(defun sumEven(l)
    (cond
        ((and (numberp l) (equal 0 (mod l 2))) l)
        ((atom l) 0)
        (T (apply '+ (mapcar #' sumEven l)))
    )
)

(defun sumOdd(l)
    (cond
        ((and (numberp l) (equal 1 (mod l 2))) l)
        ((atom l) 0)
        (T (apply '+ (mapcar #' sumOdd l)))
    )
)

(defun mainD(l)
    (- (sumEven l) (sumOdd l))
)

(print (mainD'(1 2 (3 (4 5 (2))))))

