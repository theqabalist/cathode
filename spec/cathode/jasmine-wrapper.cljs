(ns cathode.jasmine-wrapper)

(def describe js/describe)
(def it js/it)
(def fail js/fail)
(def expect js/expect)
(def create-spy (aget js/jasmine "createSpy"))
