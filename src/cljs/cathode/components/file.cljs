(ns ^:figwheel-always cathode.components.file
  (:require [reagent.session :as session]))



  (defn dragging-file [e]
    (.preventDefault e)
    (session/put! :dragging true))

  (defn undragging-file [e]
    (.preventDefault e)
    (session/put! :dragging false))

(defn capture-file [f]
  (let [ffprobe (aget js/ffmpeg "ffprobe")]
    (ffprobe f (fn [err data]
      (if err
        (js/alert err)
        (session/put! :video-data data))))))

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

(defn header []
  [:div {:class "ui top attached sub inverted blue header"} "File"])

(defn main []
  [:div {:class "ui attached segment center aligned" :on-drag-over dragging-file :on-drag-leave undragging-file :on-drop dropped-file}
    [:div {:class (if (session/get :dragging) "ui fade reveal" "ui active fade reveal")}
      (if (session/get :video-file)
        [:div {:style {:background "white"} :class "hidden content"} [:div (session/get :video-file)]]
        [:div {:style {:background "white"} :class "hidden content"} "Drag File Here"])
      [:div {:style {:background "white" :width "100%" :text-align "center"} :class " visible content"} [:i {:class "large grey plus icon"}]]]])

(defn section []
  [:div
    [header]
    [main]])
