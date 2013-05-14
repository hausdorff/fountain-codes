(ns fountain-codes.lazy-rand
  (:import [java.util Random]))

(defn lrandseq
  "Lazy sequnce of random ints in range [1..k] (inclusive)"
  [seed k]
  (let [r (Random. seed)]
    (repeatedly #(+ 1 (.nextInt r k)))))