(ns fountain-codes.encoder
  "Implements the encoding step for vanilla LT fountain codes"
  (:use [fountain-codes.sample :as sample]
        [fountain-codes.lazy-rand :as lazy-rand]))


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

(defn- pkts-subset
  "Takes a list of indices, returns a list of packets at those indices"
  [data indices]
  (map (fn [x] (.get data x)) indices))

(defn- encode-pkt
  "Creates one encoded packet"
  [data k]
  (let [deg-seed  (rand-int Integer/MAX_VALUE)
        pkts-seed (rand-int Integer/MAX_VALUE)
        deg       (lazy-rand/rint deg-seed k)
        indices   (uniform-k-sample (range 0 k) deg pkts-seed)
        pkts      (pkts-subset data indices)]
    (with-meta (combine-pkts pkts) {:k k :deg-seed deg-seed
                                    :pkts-seed pkts-seed})))

;; filename -> packet size -> packets
(defn encode
  "Generates infinite lazy stream of packets"
  [fname l]
  ; TODO: Replace this with logging
  (println "BUILDING DATA")
  (let [specs     (specify-fntn fname l)
        data      (first specs)
        k         (second specs)]
    (println (str "    Data size: " (* k l) " bytes"))
    (println (str "    # of pkts: " k))
    (repeatedly #(encode-pkt data k))))