(ns fountain-codes.lazy-rand
  (:import [java.util Random]))

(defn lrandseq
  "Lazy sequnce of random ints in range [1..k] (inclusive)"
  [seed k]
  (let [r (Random. seed)]
    (repeatedly #(+ 1 (.nextInt r k)))))

;; TODO: change me
(def renc (Random. 13))
(def rdec (Random. 13))

;; TODO: change me
(defn enc-rand-int [k]
  (+ 1 (.nextInt renc k)))
(defn dec-rand-int [k]
  (+ 1 (.nextInt dec k)))