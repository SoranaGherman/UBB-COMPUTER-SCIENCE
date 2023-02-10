(defun replaceN(l n rn)
    (cond
        ( (and (atom l) (equal l n)) rn)
        ((atom l) l)
        (T (mapcar #' (lambda (l) (replaceN l n rn)) l))
    )
)


(print (replaceN '(a (b (c)) (d) (b (f))) 'b 's))