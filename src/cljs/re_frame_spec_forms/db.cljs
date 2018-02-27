(ns re-frame-spec-forms.db
  (:require [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]))



(def router
  (r/router [["/" ::routes/landing]
             ["/layout" ::routes/layout]
             ["/spec" ::routes/spec-def]
             ["/label-input-field" ::routes/label-input]
             ["/choice-providers" ::routes/choice-providers]
             ["/ranges-input-field" ::routes/ranges-input]
             ["/form-state" ::routes/form-state]]))


(def default-db
  {::current-route-key ::routes/landing
   ::router            router})
