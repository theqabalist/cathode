(ns cathode.core.spec
  (:require [cathode.core :as core]
            [cathode.jasmine-wrapper :refer [create-spy describe it expect]]))

(describe "cathode.core" (fn []
  (describe "init!" (fn []
    (it "should called render to bootstrap the view" (fn []
      (binding [core/*render* (create-spy "*render*")]
        (core/init!)
        (-> core/*render* (expect) (.toHaveBeenCalled)))))))))
