(ns re-frame-spec-forms.db
  (:require [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]))



(def router
  (r/router [["/" ::routes/landing]
             ["/01-layout" ::routes/_01-layout]
             ["/02-spec" ::routes/_02-spec]
             ]))


(def default-db
  {::current-route-key ::routes/landing})
