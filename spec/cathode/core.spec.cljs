(ns cathode.core.spec
  (:require [reagent.core :as reagent]
            [cathode.core :as core]
            [cathode.jasmine-wrapper :refer [create-spy describe it expect]]))

(describe "cathode.core" (fn []
  (describe "init!" (fn []
    (it "should called render to bootstrap the view" (fn []
      (with-redefs [reagent/render (create-spy "render")]
        (core/init!)
        (-> (expect reagent/render) (.toHaveBeenCalled)))))))))
