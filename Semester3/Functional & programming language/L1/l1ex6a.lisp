(defun testLinear(l)
    (cond
        ((null l) T)
        ((listp (car l)) nil)
        (T (testLinear (cdr l)))
    )
)

(print (testLinear '(1 2)))


(defun checkOc(l e)
    (cond
        ((null l) 0)
        ((listp (car l)) (checkOc (cdr l) e))
        ((= (car l) e) (+ 1 (checkOc (cdr l) e)))
        (T (checkOc (cdr l) e))
    )
)


(defun replaceOnce(l e o f)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (replaceOnce (car l) e o f) (replaceOnce (cdr l) e o (+ f (checkOc(car l) e)))))
        ((and (= f 0) (= (car l) e)) (cons o (replaceOnce (cdr l) e o (+ 1 f))))
        (T (cons (car l) (replaceOnce (cdr l) e o f)))
    )
)

(print (replaceOnce '(1 2 (4 (4) 3) 4) 4 10 0))


(defun getLastHelper (vector)

    (cond

        ((null (cdr vector)) (car vector))

        (t (getLastHelper (cdr vector)))

    )

)


(defun getLast (vector)

    (cond

        ((atom vector) vector)

        (t (getLast (getLastHelper vector)))

    )

)



(defun replaceSublists (vector)

    (cond

        ((null vector) nil)

        ((atom (car vector)) (cons (car vector) (replaceSublists (cdr vector))))

        ((listp (car vector)) (cons (getLast (car vector)) (replaceSublists (cdr vector))))

    )

)


;(a (b c) (d ((e) f)))