;twice(l1...ln, n) = { null, l is empty
;			  { l1 U l1 U twice(l2...ln, n) , n = 1
;			  { l1 U twice(l2..ln, n-1), otherwise

(defun twice (l n)
    (cond
        ((null l) nil)
        ((= n 1) (cons (car l) l))
        (T (cons (car l) (twice (cdr l) (- n 1))))
    )
)

(print (twice '(10 20 30 40 50) 3))



;countList(l1...ln) = { 0, l is empty
;			  { countList(l2...ln), l1 is not a list
;			  { 1 + countList(l2...ln), otherwise

(defun countList (l)
    (cond
        ((atom l) 0)
        ((listp l) (+ 1 (apply '+ (mapcar 'countList l))))
    )
)

(print (countList '(1 2 (3 (4 5) (6 7)) 8 (9 10))))





;countNumberd(l1...ln) = { 0, l is empty
;			     { 1 + countNumbers(l2...ln), l1 is a number
;		             { countNumbers(l2...ln), otherwise

(defun countNumbers (l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ 1 (countNumbers (cdr l))))
        (T (countNumbers (cdr l)))
    )
)

(print (countNumbers '(1 2 s 5 (6) fg 5)))


;  

(defun my_append (l k)
    ( if (null l) 
        k
        (cons (car l) (my_append (cdr l) k))
    )
)

(defun association (l k)
    (cond
        ((and (null l) (null k)) nil)
        ((null l) (my_append (list (cons NIL (car k))) (association l (cdr k))))
        ((null k) (my_append (list (cons (car l) NIL)) (association (cdr l) k)))
        (T (my_append (list (cons (car l) (car k))) (association (cdr l) (cdr k))))
    )
)

(print (association '(A B C Q B V) '(X Y Z T U)))