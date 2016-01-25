(ns ^:figwheel-always cathode.components.app
  (:require [cathode.components.file :as file]
            [cathode.components.streams :as streams]))

(defn app []
  [:div {:class "ui container" :id "main-container"}
    [file/header]
    [file/section]
    [streams/section]])
