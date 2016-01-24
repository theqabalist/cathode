(ns cathode.streams)

(defn codec-name [stream]
  (aget stream "codec_name"))

(defn language [stream]
  (or (-> stream
          (aget "tags")
          ((fn [x] (or x #js {})))
          (aget "language"))
      "--"))
