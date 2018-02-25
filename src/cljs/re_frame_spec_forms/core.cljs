(ns re-frame-spec-forms.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-spec-forms.events :as events]
            [re-frame-spec-forms.views :as views]
            [re-frame-spec-forms.config :as config]
            [re-frame-spec-forms.routes :as routes]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))



(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))



(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
