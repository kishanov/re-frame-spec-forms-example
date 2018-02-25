(ns re-frame-spec-forms.views
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.subs :as subs]
            ))

(defn main-panel []
  [:div.ui.container
   [:div "hello"]])
