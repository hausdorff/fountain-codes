(ns fountain-codes.encoder
  "Implements the encoding step for vanilla LT fountain codes"
  (:use [fountain-codes.sample :as sample]))


(defn- choose-degree
  "Chooses degree of packet from distribution \rho"
  [k]
  (rand-int k))

; hack hack hack -- don't grok macros well enough to make this a macro!
(defn- combine-pkts [pkts]
  "Combines packets to create an encoded packet. Currently this means XOR'ing
  all the packets together."
  (apply map (fn [& x] (reduce bit-xor x)) pkts))

;; filename -> packet size -> `(data k)
(defn- specify-fntn
  "Generates params required to specify fountain. That is, gets data at
  filename, breaks it into a list of size-l packets, and returns
  `(data k), where k is the number of l-byte packets in the data."
  [fname l]
  (let [txt      (slurp fname)
        txtlen   (.length txt)
        trailing (apply str (repeat (- l (mod txtlen l)) " "))
        txt'     (str txt trailing)
        data     (into [] (partition-all l (map #(int %) txt')))
        k        (/ (.length txt') l)]
    `(~data ~k)))

;; filename -> packet size -> packets
(defn encode
  "Generates enough packets to reconstruct the original file"
  [fname l]
  (let [specs (specify-fntn fname l)
        data  (first specs)
        k     (second specs)
        pkts  (uniform-k-sample data (choose-degree k))]
    (println (combine-pkts pkts))))