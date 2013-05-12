(ns fountain-codes.core
  (:use [fountain-codes.encoder :as encoder]))

(defn -main [& args]
  (encoder/encode "data/rfc1951" 15))