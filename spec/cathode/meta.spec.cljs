(ns cathode.meta.spec
  (:require [cathode.meta :as meta]
            [cathode.jasmine-wrapper :refer [describe it expect]]))

(describe "cathode.meta" (fn []
  (describe "format" (fn []
    (it "should tell you the format of a container" (fn []
      (-> (meta/format #js {"format" #js {"format_name" "mkv"}}) (expect) (.toBe "mkv"))))))

  (describe "first-video" (fn []
    (let [data #js {"streams" #js [#js {"codec_type" "video" "codec_name" "h264"} #js {"codec_type" "video" "codec_name" "hevc"}]}]
      (it "should give you the first video stream" (fn []
        (-> data (meta/first-video) (aget "codec_name") (expect) (.toBe "h264")))))))

  (describe "first-audio" (fn []
    (let [data #js {"streams" #js [#js {"codec_type" "audio" "codec_name" "mp3"} #js {"codec_type" "audio" "codec_name" "flac"}]}]
      (it "should give you the first audio stream" (fn []
        (-> data (meta/first-audio) (aget "codec_name") (expect) (.toBe "mp3")))))))

  (describe "first-subtitle" (fn []
    (let [data #js {"streams" #js [#js {"codec_type" "subtitle" "codec_name" "mov_text"} #js {"codec_type" "subtitle" "codec_name" "subrip"}]}]
      (it "should give you the first subtitle stream" (fn []
        (-> data (meta/first-subtitle) (aget "codec_name") (expect) (.toBe "mov_text")))))))))
