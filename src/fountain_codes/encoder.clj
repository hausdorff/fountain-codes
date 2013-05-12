(ns fountain-codes.encoder)


(defn- choose-degree
  "Chooses degree of packet from distribution \rho"
  [k]
  (rand-int k))

;; filename -> packet size -> `(data k)
(defn- specify-fntn
  "Generates params required to specify fountain. That is, gets data at
  filename, breaks it into a list of size-l packets, and returns
  `(data k), where k is the number of l-byte packets in the data."
  [fname l]
  (let [txt  (slurp fname)
        data (into-array (partition-all l txt))
        k    (/ (.length txt) l)]
    `(~data ~k)))

;; filename -> packet size -> packets
(defn encode
  "Generates enough packets to reconstruct the original file"
  [fname l]
  (let [specs (specify-fntn fname l)
        data  (first specs)
        k     (second specs)]
    (println (aget data 1))))