(ns ^:figwheel-always cathode.components.streams
  (:require [reagent.session :as session]
            [cathode.meta :as meta]
            [cathode.streams :as streams]))

(defn remove-file []
  (session/put! :video-file nil))

(defn header []
  [:div {:id "stream-header" :class "ui attached sub inverted blue header"} "Streams"])

(defn info []
  (when-let [data (session/get :video-data)]
    [:div {:class "ui attached segment"}
      [:table {:class "ui very basic table"}
        [:thead
          [:tr
            [:th {:class "collapsing"}
              [:div {:class "ui mini basic icon button"} [:i {:class "check icon"}]]
              [:div {:class "ui mini basic icon button" :on-click remove-file} [:i {:class "remove icon"}]]
              [:div {:class "ui mini basic icon button"} [:i {:class "ellipsis horizontal icon"}]]]
            [:th {:class "center aligned"} "Codec"] [:th {:class "center aligned"} "Language"] [:th {:class "center aligned"} "Compatibility"]]]
        [:tbody
          [:tr [:td [:strong "Container"]] [:td {:class "center aligned"} (meta/format data)] [:td {:class "center aligned"} "--"] [:td {:class "center aligned"} [:i {:class "large red remove circle icon"}]]]
          [:tr [:td [:strong "Video"]] [:td {:class "center aligned"} (-> data (meta/first-video) (streams/codec-name))] [:td {:class "center aligned"} (-> data (meta/first-video) (streams/language))] [:td {:class "center aligned"} [:i {:class "large green check circle icon"}]]]
          [:tr [:td [:strong "Audio"]] [:td {:class "center aligned"} (-> data (meta/first-audio) (streams/codec-name))] [:td {:class "center aligned"} (-> data (meta/first-audio) (streams/language))] [:td {:class "center aligned"} [:i {:class "large red remove circle icon"}]]]
          (when-let [sub (-> data (meta/first-subtitle))]
            [:tr [:td [:strong "Subtitle"]] [:td {:class "center aligned"} (-> sub (streams/codec-name))] [:td {:class "center aligned"} (-> sub (streams/language))] [:td {:class "center aligned"} [:i {:class "large yellow warning circle icon"}]]])]]]))

(defn section []
  (when-let [file (session/get :video-file)]
    [:div
      [header]
      [info]]))
