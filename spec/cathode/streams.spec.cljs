(ns cathode.streams.spec
  (:require [cathode.streams :as streams]
            [cathode.jasmine-wrapper :refer [describe it expect]]))

(def test-stream #js {"codec_type" "hello"
                      "codec_name" "aname"
                      "channels" 2
                      "tags" #js {"language" "english"}})

(def expect-language (comp expect streams/language))
(def expect-compat (comp expect streams/apple-tv-compat))

(describe "cathode.streams" (fn []
  (describe "type" (fn []
    (it "should tell you the type of a stream" (fn []
      (-> test-stream (streams/type) (expect) (.toBe "hello"))))))

  (describe "codec" (fn []
    (it "should tell you the name of a stream" (fn []
      (-> test-stream (streams/codec) (expect) (.toBe "aname"))))))

  (describe "language" (fn []
    (it "should tell you the language of a stream" (fn []
      (-> test-stream (expect-language) (.toBe "english"))))

    (it "should say '--' if there is no language" (fn []
      (-> #js {} (expect-language) (.toBe "--"))))

    (it "should say '--' if language tag is undefined" (fn []
      (-> #js {"tags" #js {"language" "und"}} (expect-language) (.toBe "--"))))))

  (describe "channels" (fn []
    (it "should tell you the number of channels in a stream" (fn []
      (-> test-stream (streams/channels) (expect) (.toBe 2))))))

  (describe "apple-tv-compat" (fn []
    (describe "when you have a video stream" (fn []
      (letfn [(videostream [name] #js {"codec_name" name "codec_type" "video"})]
        (it "should accept 'h264'" (fn []
          (let [stream (videostream "h264")]
            (-> stream (expect-compat) (.toBe true)))))

        (it "should reject anything else for video streams" (fn []
          (let [stream (videostream "xvid")]
            (-> stream (expect-compat) (.toBe false)))))

        (it "should reject blank codec names" (fn []
          (let [stream (videostream nil)]
            (-> stream (expect-compat) (.toBe false))))))))

    (describe "when you have an audio stream" (fn []
      (letfn [(audiostream [name channels] #js {"codec_name" name "channels" channels "codec_type" "audio"})]
        (describe "when you have <= 2 channels" (fn []
          (describe "when you have aac" (fn []
            (let [stereo (audiostream "aac" 2)
                  mono (audiostream "aac" 1)]
              (it "should accept either" (fn []
                (-> stereo (expect-compat) (.toBe true))
                (-> mono (expect-compat) (.toBe true)))))))

          (describe "when you have ac3" (fn []
            (let [stereo (audiostream "ac3" 2)
                  mono (audiostream "ac3" 1)]
              (it "should accept either" (fn []
                (-> stereo (expect-compat) (.toBe true))
                (-> mono (expect-compat) (.toBe true)))))))

          (describe "when you have any other codec" (fn []
            (let [stereo (audiostream "mp3" 2)
                  mono (audiostream "mp2" 1)]
              (it "should reject both" (fn []
                (-> stereo (expect-compat) (.toBe false))
                (-> mono (expect-compat) (.toBe false)))))))))

        (describe "when you have > 2 channels (5.1)" (fn []
          (describe "when you have aac" (fn []
            (let [surround (audiostream "aac" 6)]
              (it "should reject it" (fn []
                (-> surround (expect-compat) (.toBe false)))))))

          (describe "when you have ac3" (fn []
            (let [surround (audiostream "ac3" 6)]
              (it "should accept it" (fn []
                (-> surround (expect-compat) (.toBe true)))))))

          (describe "when you have any other codec" (fn []
            (let [surround (audiostream "flac" 6)]
              (it "should reject it" (fn []
                (-> surround (expect-compat) (.toBe false))))))))))))

    (describe "when you have a subtitle stream" (fn []
      (letfn [(substream [name] #js {"codec_type" "subtitle" "codec_name" name})]
        (it "should accept mov_text" (fn []
          (-> "mov_text" (substream) (expect-compat) (.toBe true))))

        (it "should reject anything else" (fn []
          (-> "subrip" (substream) (expect-compat) (.toBe false))))

        (it "should reject blank codec names" (fn []
          (-> nil (substream) (expect-compat) (.toBe false)))))))

    (describe "when you have a container" (fn []
      (letfn [(contstream [type] #js {"codec_type" "container" "codec_name" type})]
        (it "should accept mp4" (fn []
          (-> "abc,123,mp4,hello" (contstream) (expect-compat) (.toBe true))))

        (it "should reject anything else" (fn []
          (-> "flv" (contstream) (expect-compat) (.toBe false)))))))))))
