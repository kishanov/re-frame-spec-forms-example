(ns re-frame-spec-forms.events
  (:require [re-frame.core :as re-frame]
            [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]
            [re-frame-spec-forms.db :as db]))



(defn on-navigate
  "A function which will be called on each route change."
  [name params query]
  (re-frame/dispatch [::on-navigate name params query]))


(r/start! db/router {:default     ::routes/landing
                     :on-navigate on-navigate})


(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))


(re-frame/reg-event-db
  ::on-navigate
  (fn [db [_ name params query]]
    (assoc db ::db/current-route-key name)))


(re-frame/reg-event-db
  ::change-route-key
  (fn [db [_ new-route-key]]
    (set! js/window.location.hash (r/resolve db/router new-route-key))
    db))
