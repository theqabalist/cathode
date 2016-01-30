(ns ^:figwheel-always cathode.components.streams
  (:require [reagent.session :as session]
            [cathode.meta :as meta]
            [cathode.streams :as streams]
            [clojure.string :as string]))

(defn remove-file []
  (session/put! :video-file nil))

(defn header []
  [:div {:id "stream-header" :class "ui attached sub inverted blue header"} "Streams"])

(defn stream-info [stream]
  [:tr
    [:td [:strong (-> stream (streams/type) (string/capitalize))]]
    [:td {:class "center aligned"} (streams/codec stream)]
    [:td {:class "center aligned"} (streams/language stream)]
    [:td {:class "center aligned"} [:i {:class "large green check circle icon"}]]])

(defn file-menu []
  [:th {:class "collapsing"}
    [:div {:class "ui mini basic icon button"} [:i {:class "check icon"}]]
    [:div {:class "ui mini basic icon button" :on-click remove-file} [:i {:class "remove icon"}]]
    [:div {:class "ui mini basic icon button"} [:i {:class "ellipsis horizontal icon"}]]])

(defn file-header [name]
  [:th {:class "center aligned"} name])

(defn info []
  (when-let [data (session/get :video-data)]
    (let [format (meta/format data)
          first-video (meta/first-video data)
          first-audio (meta/first-audio data)]
      [:div {:class "ui attached segment"}
        [:table {:class "ui very basic table"}
          [:thead
            [:tr
              [file-menu]
              [file-header "Codec"]
              [file-header "Language"]
              [file-header "Compatibility"]]]
          [:tbody
            [stream-info #js {"codec_name" format "codec_type" "container" "codec_language" "--"}]
            [stream-info first-video]
            [stream-info first-audio]
            (when-let [first-sub (meta/first-subtitle data)]
              [stream-info first-sub])]]])))

(defn section []
  (when-let [file (session/get :video-file)]
    [:div
      [header]
      [info]]))
