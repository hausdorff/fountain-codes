(ns fountain-codes.decoder
  "Implements decoding step for vanilla LT fountain codes"
  (:use [fountain-codes.lazy-rand :as lazy-rand]
        [fountain-codes.sample :as sample]
        [clojure.core.match :only (match)])
  (:import [java.util PriorityQueue]))


(defn- pkt-deg
  "Returns packet's degree"
  [pkt]
  (let [{:keys [k deg-seed]} (meta pkt)]
    (lazy-rand/rint deg-seed k)))

(defn- pkt-indices
  "Returns list of indices of data packets used to create this original packet"
  [pkt]
  (let [{:keys [k pkts-seed]} (meta pkt)
        deg (pkt-deg pkt)]
    (uniform-k-sample (range 0 k) deg pkts-seed)))

(defn decode
  "Takes a stream of packets and the packet size, returns a decoded file"
  [pkt-strm]
  ; TODO: replace this with logging
  (println "ENCODING DATA")
  (let [init-pkts (take 3000 pkt-strm)
        q         (PriorityQueue.)
        decd      #{}]
    (println "DECODING DATA")
    (println (pkt-indices (first init-pkts)))))