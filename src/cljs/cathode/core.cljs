(ns ^:figwheel-always cathode.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [clojure.string :as string]
              [cathode.streams :as streams]
              [cathode.components.app :refer [app]]))

; (defn home-page []
;   [:div {:class "ui container" :id "main-container"}
;       [:div {:class "ui top attached sub inverted blue header"}
;         "File"]
;       [:div {:class "ui attached segment center aligned" :on-drag-over dragging-file :on-drag-leave undragging-file :on-drop dropped-file}
;         [:div {:class (if (session/get :dragging) "ui fade reveal" "ui active fade reveal")}
;           (if (session/get :video-file)
;             [:div {:style {:background "white"} :class "hidden content"} [:div (session/get :video-file)]]
;             [:div {:style {:background "white"} :class "hidden content"} "Drag File Here"])
;           [:div {:style {:background "white" :width "100%" :text-align "center"} :class " visible content"} [:i {:class "large grey plus icon"}]]]]
;       [streams-section]
;       [:div {:class "ui attached sub inverted blue header"} "Queue"]
;       [:div {:class "ui attached segment"}
;         [:table {:class "ui very basic table"}
;           [:thead
;             [:tr [:th "File"] [:th {:class "center aligned collapsing"} "Status"]]]
;           [:tbody
;             [:tr [:td "/very/long/path/to/file/name.mkv"] [:td {:class "center aligned"} [:i {:class "green check icon"}]]]
;             [:tr [:td "/very/long/path/to/file/name.mkv"] [:td {:class "center aligned"} [:i {:class "green check icon"}]]]
;             [:tr
;               [:td
;                 [:div "/very/long/path/to/file/name.mkv"]
;                 [:div {:class "ui indicating tiny progress active" :data-percent "50"} [:div {:class "bar" :style {:width "50%"}}]]]
;               [:td {:class "center aligned"} [:i {:class "clock icon"}]]]]]]])
(def ^:dynamic *render* reagent/render)
(defn init! []
  (*render* [app] (.getElementById js/document "app")))
