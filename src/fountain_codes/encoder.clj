(ns fountain-codes.encoder)

;; Chooses degree of packet from distribution \rho
(defn choose-degree [k]
  (rand-int k))

;; filename -> packet size -> `(data k)
;; Generates params required to specify fountain. That is, gets data at
;; filename, breaks it into a list of size-l packets, and returns
;; `(data k), where k is the number of l-byte packets in the data.
(defn specify-fntn [fname l]
  (let [txt  (slurp fname)
        data (into-array (partition-all l txt))
        k    (/ (.length txt) l)]
    `(~data ~k)))

;; filename -> packet size -> packets
;; generates enough packets to reconstruct the original file
(defn encode [fname l]
  (let [specs (specify-fntn "data/rfc1951" 15)
        data  (first specs)
        k     (second specs)]
    (println (aget data 1))))