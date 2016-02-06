(ns ^:figwheel-always cathode.components.app
  (:require [cathode.components.file :as file]
            [cathode.components.streams :as streams]
            [cathode.components.queue :as queue]))

(defn app []
  [:div {:class "ui container" :id "main-container"}
    [file/section]
    [streams/section]
    [queue/section]])
