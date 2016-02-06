(ns cathode.meta
  (:require-macros [swiss.arrows :refer [-<>]]))

(defn codec-type-is? [type]
  (fn [x] (= (aget x "codec_type") (name type))))

(defn first-stream-of-type [type]
  (fn [data]
    (-<> data
         (aget <> "streams")
         (filter (codec-type-is? type) <>)
         (first <>))))

(def first-video (first-stream-of-type :video))
(def first-audio (first-stream-of-type :audio))
(def first-subtitle (first-stream-of-type :subtitle))

(defn format [data]
  (-> data (aget "format") (aget "format_name")))
