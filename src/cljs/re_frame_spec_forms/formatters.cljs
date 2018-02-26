(ns re-frame-spec-forms.formatters
  (:require [cljs.pprint :as pp]
            [cljs.repl :as r]
            [clojure.walk :as walk]))



(defn formatted-code [data]
  [:pre
   [:code {:class "clojure"}
    (with-out-str
      (pp/pprint
        data))]])


(defn inspect [data & [pprint?]]
  [:pre
   [:code
    (if pprint?
      (with-out-str (pp/pprint data))
      (.stringify js/JSON (clj->js data) nil 2))]])
