(ns fountain-codes.lazy-rand
  (:import [java.util Random]))

(defn lrandseq
  "Lazy sequnce of random ints in range [1..k] (inclusive)"
  [seed k]
  (let [r (Random. seed)]
    (repeatedly #(+ 1 (.nextInt r k)))))

;; TODO: change me
(defn rint [seed k]
  (let [r (Random. seed)]
    (+ 1 (.nextInt r k))))
(defn newrand [seed]
  (Random. seed))
(defn nint [r k]
  (.nextInt r k))