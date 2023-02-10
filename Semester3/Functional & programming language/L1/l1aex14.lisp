(defun removeFirstOcc(l elem)
  (cond
    ((null l) nil)
    ((equal (car l) elem) (cdr l))
    (T (cons (car l) (removeFirstOcc (cdr l) elem)))
  )
)


(defun unionSets(l1 l2)
  (cond
    ((and (null l1) (null l2)) nil)
    ((null l1) l2)
    ((null l2) l1)
    (t (cons (car l1) (unionSets (cdr l1) (removeFirstOcc l2 (car l1)))))
  )
)

(print (unionSets '(1 2 3) '(3 4 5 6)))

(defun product(l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (* (car l) (product (cdr l))))
        ((atom (car l)) (product (cdr l)))
        (T (* (product(car l)) (product(cdr l))))
    )
)

(print (product '(1 2 (a c(p)4)2) ))



(defun insertOk(l elem)
  (cond
    ((null l) (list elem))
    ((< elem (car l)) (cons elem l))
    (t (cons (car l) (insertOk (cdr l) elem)))
  )
)

(defun mySort(l)
  (cond
    ((null l) nil)
    (T (insertOk (mySort (cdr l)) (car l)))
  )
)

(print (mySort '(1 3 2 0)))


(defun findMinBet2(a b)
  (cond
    ((and (not (numberp a)) (not (numberp b))) nil)
    ((not (numberp a)) b)
    ((not (numberp b)) a)
    ((< a b) a)
    (t b)
  )
)

(defun minList(l)
    (cond
        ((and (null (cdr l)) (atom (car l)))   (car l))
        (T (findMinBet2 (car l) (minList(cdr l))))
    )
)

(print (minList '(2 3 1 4 3)))


(defun posMin(l min pos)
    (cond
        ((null l) nil)
        ((equal (car l) min) (cons pos (posMin (cdr l) min (+ pos 1))))
        (T (posMin (cdr l) min (+ pos 1)))
    )
)

(defun mainD (l)
  (posMin l (minList l) 0)
)

(print (mainD '(2 3 1 4 3 1)))

