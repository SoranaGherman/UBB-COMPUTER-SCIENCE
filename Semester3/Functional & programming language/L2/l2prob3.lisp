; 3. Define a function to tests the membership of a node in a n-tree represented as (root
;    list_of_nodes_subtree1 ... list_of_nodes_subtreen)
;    Eg. tree is (a (b (c)) (d) (E (f))) and the node is "b" => true


;countNode(l1...ln node) = { 1, l1-atom and l1 = node
;                          { 0, l1-atom and l1 != node
;                          { countNode(l2,node) + countNode(l3,node) + .. + countNode(ln, node), otherwise
;


(defun countNode (tree node)
    (cond
        ((and (atom tree) (equal tree node)) 1)
        ((atom tree) 0)
        (t (apply '+ (mapcar #'(lambda (newTree) (countNode newTree node)) tree)))
    )
)

;main(l1...ln node) = { NIL, countNode(l1...ln node) = 0
;                     { T, otherwise
;


(defun main (tree node)
    (cond
        ((equalp (countNode tree node) 0) nil)
        (t t)
    )
)

(print(main '(a (b (c)) (d) (E (f))) 'b))