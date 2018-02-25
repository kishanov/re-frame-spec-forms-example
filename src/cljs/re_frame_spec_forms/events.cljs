(ns re-frame-spec-forms.events
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))
