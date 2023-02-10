(defun prod(l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (* (car l) (prod (cdr l))))
        (T (prod(cdr l)))
    )
)

(print (prod '(1 2 (3 (4)) 2)))

(defun countAppearances (vector element)
    (cond
        ((null vector) 0)
        ((listp (car vector)) (countAppearances (cdr vector) element))
        ((= (car vector) element) (+ 1 (countAppearances (cdr vector) element)))
        (t (countAppearances (cdr vector) element))
    )
)

(defun replaceE(l e o found)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (replaceE (car l) e o found) (replaceE (cdr l) e o (+ found (countAppearances (car l) e)))))
        ((and (= 0 found) (equal (car l) e)) (cons o (replaceE (cdr l) e o (+ 1 found))))
        (T (cons (car l) (replaceE (cdr l) e o found)))
    )
)

(print (replaceE '(1 2 3 (4) 5 4) 4 10 0))

(defun countAtom(l e)
    (cond
        ((null l) 0)
        ((listp (car l)) (+ (countAtom (car l) e) (countAtom (cdr l) e)))
        ((equal (car l) e) (+ 1 (countAtom (cdr l) e)))
        (T (countAtom (cdr l) e))
    )
)

(defun removeAtom(l e)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (removeAtom (car l) e) (removeAtom (cdr l) e)))
        ((equal (car l) e) (removeAtom (cdr l) e))
        (T (cons (car l) (removeAtom (cdr l) e)))
    )
)

(defun pairsA(l)
    (cond
        ((null l) nil)
        (T (cons (list (car l) (countAtom l (car l) )) (pairsA (removeAtom (cdr l) (car l)))))
    )
)

(print (pairsA '(A B A B C D)))


