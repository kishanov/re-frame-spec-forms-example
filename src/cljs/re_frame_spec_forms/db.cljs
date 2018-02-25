(ns re-frame-spec-forms.db
  (:require [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]))



(def router
  (r/router [["/" ::routes/landing]
             ["/01-layout" ::routes/_01-layout]]))


(def default-db
  {::current-route-key ::routes/landing})
