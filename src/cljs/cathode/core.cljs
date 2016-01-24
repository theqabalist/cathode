(ns cathode.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [clojure.string :as string]
              [cathode.meta :as meta]
              [cathode.streams :as streams]))

(defn capture-file [f]
  (let [ffprobe (aget js/ffmpeg "ffprobe")]
   (ffprobe f (fn [err data]
     (if err
       (js/alert err)
       (session/put! :video-data data))))))

(defn remove-file []
  (session/put! :video-file nil))

(defn dragging-file [e]
  (.preventDefault e)
  (session/put! :dragging true))

(defn undragging-file [e]
  (.preventDefault e)
  (session/put! :dragging false))

(defn dropped-file [e]
  (.preventDefault e)
  (session/put! :dragging false)
  (let [file (-> e
                (aget "dataTransfer")
                (aget "files")
                (aget 0)
                (aget "path"))]
      (session/put! :video-file file)
      (capture-file file)))

(defn streams-section []
  (when-let [file (session/get :video-file)]
    (let [data (session/get :video-data)]
      [:div
        [:div {:id "stream-header" :class "ui attached sub inverted blue header"}
          "Streams"]
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
                [:tr [:td [:strong "Subtitle"]] [:td {:class "center aligned"} (-> sub (streams/codec-name))] [:td {:class "center aligned"} (-> sub (streams/language))] [:td {:class "center aligned"} [:i {:class "large yellow warning circle icon"}]]])]]]])))

(defn home-page []
  [:div {:class "ui container" :id "main-container"}
      [:div {:class "ui top attached sub inverted blue header"}
        "File"]
      [:div {:class "ui attached segment center aligned" :on-drag-over dragging-file :on-drag-leave undragging-file :on-drop dropped-file}
        [:div {:class (if (session/get :dragging) "ui fade reveal" "ui active fade reveal")}
          (if (session/get :video-file)
            [:div {:style {:background "white"} :class "hidden content"} [:div (session/get :video-file)]]
            [:div {:style {:background "white"} :class "hidden content"} "Drag File Here"])
          [:div {:style {:background "white" :width "100%" :text-align "center"} :class " visible content"} [:i {:class "large grey plus icon"}]]]]
      [streams-section]
      [:div {:class "ui attached sub inverted blue header"} "Queue"]
      [:div {:class "ui attached segment"}
        [:table {:class "ui very basic table"}
          [:thead
            [:tr [:th "File"] [:th {:class "center aligned collapsing"} "Status"]]]
          [:tbody
            [:tr [:td "/very/long/path/to/file/name.mkv"] [:td {:class "center aligned"} [:i {:class "green check icon"}]]]
            [:tr [:td "/very/long/path/to/file/name.mkv"] [:td {:class "center aligned"} [:i {:class "green check icon"}]]]
            [:tr
              [:td
                [:div "/very/long/path/to/file/name.mkv"]
                [:div {:class "ui indicating tiny progress active" :data-percent "50"} [:div {:class "bar" :style {:width "50%"}}]]]
              [:td {:class "center aligned"} [:i {:class "clock icon"}]]]]]]])

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
