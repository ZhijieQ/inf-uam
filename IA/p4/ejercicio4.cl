;;;Ejercicio4
(setq *rule-list* '(
(R1 (accept ?l) :- ((start ?q) (accept ?q ?l)))
(R2 (accept ?q ()) :-( (final ?q)))
(R3 (accept ?q (?s . ?l) :- (arc ?q ?r ?s) (accept ?r ?l)))
(R4 (arc 0 1 lambda))
(R5 (arc 0 2 1))
(R6 (arc 1 5 0))
(R7 (arc 1 2 1))
(R8 (arc 2 3 0))
(R9 (arc 5 4 1))
(R10 (arc 4 3 0))
(R11 (arc 3 4 lambda))
(R12 (final 3))
(R13 (final 4))
(R14 (start 0))
(R15 (arc 0 0 0))
(R16 (arc  0 0 1))
))


(R4 (start 0))
(R5 (arc 0 0 0))
(R6 (arc  0 0 1))
(R7 (arc 0 1 lambda))
(R8 (arc 0 2 1))
(R9 (arc 1 5 0))
(R10 (arc 1 2 1))
(R11 (arc 2 3 0))
(R12 (arc 3 4 lambda))
(R13 (arc 5 4 1))
(R14 (arc 4 3 0))
(R15 (final 3))
(R16 (final 4))

