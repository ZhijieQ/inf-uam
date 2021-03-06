;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; AD-recur(x y)
;;;	Calcula la desviacion absoluta entre 2 vectores
;;;
;;;	INPUT:
;;;		x: vector
;;;		y: vector
;;;	OUTPUT:
;;;		Desviacion absoluta
;;;
(defun AD-recur (x y)
	(if (null x)
		0
	(if (null y)
		0
	(if( null (cdr x))
		(abs (- (car x) (car y)))
	(+ (abs (- (car x) (car y))) (AD-recur(cdr x) (cdr y)))))))

;;;	EJEMPLOS:
;;;		(AD-recur '() '()); -> 0	;caso especial
;;;		(AD-recur '(1 2) '(1); -> 0	;caso especial
;;;		(AD-recur '(1 -1 1) '(0 0 0); -> 3 ;caso tipico 
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
