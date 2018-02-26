(ns forms.core
  (:require [re-frame.core :as re-frame]))


(def root-db-path ::forms)


(re-frame/reg-sub
  ::forms-data
  (fn [db]
    (get-in db root-db-path)))


(re-frame/reg-sub
  ::field-value
  :<- [::forms-data]
  (fn [forms-data [_ form-id field-path]]
    (get-in forms-data (vec (cons form-id field-path)))))


(re-frame/reg-event-db
  ::set-field-value
  (fn [_ db form-id field-path new-value]
    (assoc-in db (vec (cons form-id field-path)) new-value)))

