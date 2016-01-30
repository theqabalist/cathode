(ns cathode.streams.spec
  (:require [cathode.streams :as streams]))

(def describe js/describe)

(def it js/it)

(def fail js/fail)

(def expect js/expect)

(describe "cathode.streams" (fn []
  (describe "type" (fn []
    (it "should tell you the type of a stream" (fn []
      (let [stream #js {"codec_type" "hello"}]
        (-> (expect (streams/type stream)) (.toBe "hello")))))))))
