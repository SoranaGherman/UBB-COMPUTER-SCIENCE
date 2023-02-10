(defun insertAtPosN(l elem pos)
    (cond
        ((null l) nil)
        ((= pos 1) (cons elem l))
        (T (cons (car l) (insertAtPosN (cdr l) elem (- pos 1))))
    )
)

(print (insertAtPosN '(1 2 3 4) 10 2))


(defun sum(l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ (car l) (sum(cdr l))))
        ((atom (car l))    (sum(cdr l)))
        (T (+ (sum(car l)) (sum(cdr l))))
    )
)

(print (sum '(1 2 ((a b 3) 1)z)))


(defun setsEqual(l p)
    (and (null l) (null p) T)
    (or (null l) (null p) nil)
    (not (= (car l) (car p) nil))
    (T (setsEqual (cdr l) (cdr p) ))
)

(print (setsEqual '(1 2 3) '(1 2 3)))