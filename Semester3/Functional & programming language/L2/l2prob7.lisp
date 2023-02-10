(defun mySubstitute(l elem p)
  (cond
    ((and (not (equal l elem)) (atom l))  l)
    ((and (equal l elem) (atom l)) p)
    (t (mapcar #' (lambda (l) (mySubstitute l elem p)) l))
  )
)


(print  (mySubstitute '(1 2 (1 (1 3 (1)))) 1 '(10 11 12)))