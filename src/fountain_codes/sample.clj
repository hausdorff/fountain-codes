(ns fountain-codes.sample
  "Simple package for sampling data"
  (:use [fountain-codes.lazy-rand :as lazy-rand]))


(defn- rs-create
  "The reservoir sampler is a data structure allowing selection of a
  uniformly-likely k-sample of an n-element list in O(n) time and space.
  Specified in Algorithm R of Vitter 1985.

  `size` metadatum reflects total capacity of sampler; `n` reflects the current
  number of times `insert` has been called.

  NOTE: I couldn't find standard library to actually do reservoir sampling, so
  I modified the Apache-licensed code I found by Adam Ashenfelter:
  https://gist.github.com/ashenfad/2969087"
  [size]
  (with-meta [] {:size size :n 0}))

(defn- rs-insert
  "Inserts value into reservoir sampler (created using `rs-create`)."
  [sampler v]
  (let [{:keys [size n]} (meta sampler)
        n' (inc n)
        index (lazy-rand/enc-pkts n')]
    (with-meta (cond (<= n' size)    (conj sampler v)
                     ;(= n' size)    (shuffle (conj sampler v))
                     (< index size) (assoc sampler index v)
                     :else          sampler)
      {:size size
       :n n'})))

(defn uniform-k-sample
  "Takes a uniformly-likely k-sample of some n-element list in O(n) time/space"
  [arr k]
  (reduce rs-insert (rs-create k) arr))