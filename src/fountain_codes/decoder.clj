(ns fountain-codes.decoder
  "Implements decoding step for vanilla LT fountain codes"
  (:use [fountain-codes.lazy-rand :as lazy-rand]
        [fountain-codes.sample :as sample]
        [clojure.core.match :only (match)]))


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

(defn src-pkt-decoded?
  "Take index of source packet and checks to see if it's decoded"
  [pkt-idx decd]
  (contains? decd pkt-idx))

(defn src-pkt-deps
  "Returns the list of encoded packets used the packet at `pkt-idx` in the
  encoding step, or nil if there are none"
  [pkt-idx deps]
  (get deps pkt-idx))

(defn num-src-pkt-deps
  "Returns number of `src-pkt-deps` for packet at `pkt-idx`"
  [pkt-idx deps]
  (.size (src-pkt-deps pkt-idx deps)))

(defn decode-loop
  [pkt-strm unresolved decd deps curr-msg]
  (cond
   (= 0 unresolved) (map (fn [s] (apply str s)) (repeat 3 (repeat 3 \c)))
   :else (do
           (println "DECODE LOGIC GOES HERE"))
         ))

(defn decode
  "Takes a stream of packets and the packet size, returns a decoded file"
  [pkt-strm]
  ; TODO: replace this with logging
  (println "ENCODING DATA")
  (let [{:keys [k l]} (meta pkt-strm)
        decd          #{}  ; #{indices of decoded pkts}
        deps          {}   ; src pkt idx -> #{encoded pkt dependencies}
        blnk-msg      (repeat k (repeat l \space))]  ; blank message
    (println "DECODING DATA")
    (println k)
    (decode-loop pkt-strm k decd deps blnk-msg)))