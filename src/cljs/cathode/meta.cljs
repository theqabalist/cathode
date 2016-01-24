(ns cathode.meta
  (:require-macros [swiss.arrows :refer [-<>]]))

(defn first-stream-of-type [type]
  (fn [data]
    (-<> data
         (aget <> "streams")
         (filter (fn [x] (= (aget x "codec_type") (name type))) <>)
         (first <>))))

(def first-video (first-stream-of-type :video))
(def first-audio (first-stream-of-type :audio))
(def first-subtitle (first-stream-of-type :subtitle))

(defn format [data]
  (-> data (aget "format") (aget "format_name")))
