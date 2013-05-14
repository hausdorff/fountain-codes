(ns fountain-codes.lazy-rand
  (:import [java.util Random]))

(defn lrandseq
  "Lazy sequnce of random ints in range [1..k] (inclusive)"
  [seed k]
  (let [r (Random. seed)]
    (repeatedly #(+ 1 (.nextInt r k)))))

;; TODO: change me
(def renc-deg (Random. 13))
(def renc-pkts (Random. 27))
(def rdec-deg (Random. 13))
(def rdec-pkts (Random. 27))

;; TODO: change me
(defn enc-deg [k]
  (+ 1 (.nextInt renc-deg k)))
(defn enc-pkts [k]
  (+ 1 (.nextInt renc-pkts k)))
(defn dec-deg [k]
  (+ 1 (.nextInt rdec-deg k)))
(defn dec-pkts [k]
  (+ 1 (.nextInt rdec-pkts k)))