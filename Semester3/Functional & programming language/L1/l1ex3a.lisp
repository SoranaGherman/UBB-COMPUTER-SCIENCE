(defun insertElem(l e pos)
  (cond
    ((null l) nil)
    ((equal (mod pos 2) 0) (cons (car l) (cons e (insertElem (cdr l) e (+ 1 pos)))))
    (t (cons (car l) (insertElem (cdr l) e (+ 1 pos))))
  )
)

(defun mainA(l e)
  (insertElem l e 1)
)

(print (mainA '(1 2 3 4 5 6) 10 ))


(defun mygcd(a b)
    (cond
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((= b 0) a)
        (T (mygcd b (mod a b))) 
    )
)


(defun gcdL(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (mygcd (gcdL (car l)) (gcdL (cdr l))))
        (T (mygcd (car l) (gcdL (cdr l))))
    )
)

(print (gcdL '(4 (4 8 (12)))))


(defun nrOfOccurences(l c elem)
  (cond
    ((null l) c)
    ((listp (car l)) (+ (nrOfOccurences (car l) 0 elem) (nrOfOccurences (cdr l) c elem)))
    ((equal (car l) elem) (nrOfOccurences (cdr l) (+ 1 c) elem))
    (t (nrOfOccurences (cdr l) c elem))
  )
)


(defun mainD(l elem)
  (nrOfOccurences l 0 elem)
)


(print (mainD '(1 1 2 (3 4 (1 2 ) 1 )) 1))