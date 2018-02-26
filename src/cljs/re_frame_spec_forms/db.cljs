(ns re-frame-spec-forms.db
  (:require [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]))



(def router
  (r/router [["/" ::routes/landing]
             ["/01-layout" ::routes/layout]
             ["/02-spec" ::routes/spec-def]
             ["/03-label-input-field" ::routes/label-input]
             ["/04-ranges-input-field" ::routes/ranges-input]
             ["/05-form-state" ::routes/form-state]]))


(def default-db
  {::current-route-key ::routes/landing})
