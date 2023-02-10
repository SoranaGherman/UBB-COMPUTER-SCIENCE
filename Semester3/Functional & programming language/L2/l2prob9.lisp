(defun removeAux(l)
  (cond
    ((null l) nil)
     ((and T (equal (car l) nil)) (removeAux (cdr l)))
    ((atom (car l)) (cons (car l) (removeAux (cdr l))))
    (t (cons (removeAux (car l)) (removeAux (cdr l))))
  )
)

(defun removeAll(l e)
    (cond
        ((and (atom l) (equal l e)) nil)
        ((atom l) l)
        (T (apply #'removeAux(list (mapcar #' (lambda (l) (removeAll l e)) l))))
    )
)

(print (removeAll '(1 2 3 (1 1 2 (1) 4)) 1))