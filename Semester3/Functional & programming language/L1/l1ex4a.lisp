(defun myAppend(l1 l2)
    (cond
        ((and (null l1) (listp l2)) l2)
        ((and (null l1)) (list l2))
        (T (cons (car l1) (myAppend (cdr l1) l2)))
    )
)


(defun getList(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (myAppend (getList (car l)) (getList (cdr l))))
        (T (cons (car l) (getList (cdr l))))
    )
)


(print (getList '(((A B) C) (D E))))


;; invertList(l1 l2 ... ln, result) = result, if n = 0

;;                                  = invertList(l2 l3 ... ln, {l1} U result), if l1 is an atom

;;                                  = result U invertList(l1, {}) U invertList(l2 l3 ... ln, {}), if l1 is a list

(defun reverseCont(l res)
    (cond 
        ((null l) res)
        ((atom (car l)) (reverseCont (cdr l) (myAppend (list (car l)) res)))
        (T (myAppend (myAppend res (list(reverseCont (car l) nil))) (reverseCont (cdr l) nil)))
    )
)


(print (reverseCont '(a b c (d (e f) g h i)) nil))

 