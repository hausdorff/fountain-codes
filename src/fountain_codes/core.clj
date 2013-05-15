(ns fountain-codes.core
  (:use [fountain-codes.encoder :as encoder]
        [fountain-codes.decoder :as decoder]))

(defn -main [& args]
  (let [l        15
        pkt-strm (encoder/encode "data/paragraph" l)]
    (decoder/decode pkt-strm)))