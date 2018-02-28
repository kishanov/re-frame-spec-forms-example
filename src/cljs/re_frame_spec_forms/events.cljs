(ns re-frame-spec-forms.events
  (:require [re-frame.core :as re-frame]
            [bide.core :as r]
            [re-frame-spec-forms.routes :as routes]
            [re-frame-spec-forms.db :as db]

            [forms.core :as forms]))



(defn on-navigate
  "A function which will be called on each route change."
  [name params query]
  (re-frame/dispatch [::on-navigate name params query]))


(r/start! db/router {:default     ::routes/landing
                     :on-navigate on-navigate})


(re-frame/reg-event-db
  ::initialize-db
  (fn [db]
    (if (::db/current-route-key db)
      db
      db/default-db)))


(re-frame/reg-event-db
  ::on-navigate
  (fn [db [_ name params query]]
    (-> db
        (assoc ::db/current-route-key name)
        (assoc-in forms/root-db-path nil))))


(re-frame/reg-event-db
  ::change-route-key
  (fn [db [_ new-route-key]]
    (set! js/window.location.hash (r/resolve db/router new-route-key))
    db))


(defn form-valid? [form-value]
  (not= (:label form-value) "asdf"))


(re-frame/reg-event-fx
  ::simulate-api-server-response
  (fn [{:keys [db]} [_ form-id]]
    {:db       (let [form-value (get-in db (conj forms/value-db-path form-id))]
                 (assoc-in db [::db/api-response form-id]
                           (if (form-valid? form-value)
                             201
                             422)))
     :dispatch [::forms/set-flag-value form-id ::forms/submitting? false]}))


(re-frame/reg-sub
  ::api-server-response
  (fn [db [_ form-id]]
    (get-in db [::db/api-response form-id])))


(re-frame/reg-event-fx
  ::submit-form
  (fn [_ [_ form-id]]
    {:dispatch-later [{:ms       5000
                       :dispatch [::simulate-api-server-response form-id]}]}))
