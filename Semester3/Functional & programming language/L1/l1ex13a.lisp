(defun removeN(l n c)
   (cond
        ((null l) nil)
        ((equal 0 (mod c n)) (removeN (cdr l) n (+ 1 c)))
        (T (cons (car l) (removeN (cdr l) n (+ 1 c))))
   )
)

(defun main(l n)
    (removeN l n 0)
)

(print (main '(1 2 3 4 5 6 7 8 9 10) 3 ))


(defun listLen(l)
    (cond
        ((null l) 0)
        (T (+ 1 (listLen(cdr l))))
    )
)

(defun valley (l decreasing)
	(cond
		((= (listLen l) 1) (if decreasing nil T))
		((and (> (car l) (cadr l)) (not decreasing)) nil)
		((and (< (car l) (cadr l)) decreasing) (valley (cdr l) nil))
		(T (valley (cdr l) decreasing))
	)
)

(defun mainB(l)
  (cond
    ((null l) nil)
    ((null (cadr l)) nil)
    ((null (caddr l)) nil)
    ((< (car l) (cadr l)) nil)
    (t (valley l T))
  )
)

(print (mainB '(10 8 6 17 19 20)))


(defun myMin(a b)
  (cond
    ((and (not (numberp a)) (not (numberp b))) nil)
    ((not (numberp a)) b)
    ((not (numberp b)) a)
    ((< a b) a)
    (t b)
  )
)

(defun findMin(l)
  (cond
    ((and (null (cdr l)) (atom (car l))) (car l))
    ((listp (car l)) (myMin (findMin (car l)) (findMin (cdr l))))
    (t (myMin (car l) (findMin (cdr l))))
  )
)

(print (findMin '(3 2(3(1)3)5)))


(defun deleteMin(l min)
    (cond
        ((null l) nil)
        ((equal min (car l)) (deleteMin (cdr l) min))
        (T (cons (car l) (deleteMin (cdr l) min)))
    )
)

(defun mainMin(l)
    (deleteMin l (findMin l))
)

(print (mainMin '(2 1 3 4 1 5 1)))