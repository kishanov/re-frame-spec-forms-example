(ns forms.core
  (:require [re-frame.core :as re-frame]
            [cljs.spec.alpha :as s]))


(def root-db-path [::forms])
(def value-db-path (conj root-db-path ::value))


(re-frame/reg-sub
  ::values
  (fn [db]
    (get-in db value-db-path)))


(re-frame/reg-sub
  ::field-value
  :<- [::values]
  (fn [forms-data [_ form-id field-path]]
    (get-in forms-data (vec (cons form-id field-path)))))


(re-frame/reg-event-db
  ::set-field-value
  (fn [db [_ form-id field-path new-value]]
    (assoc-in db (vec (concat value-db-path (cons form-id field-path))) new-value)))


(def flags-db-path (conj root-db-path ::flags))


(re-frame/reg-sub
  ::flags
  (fn [db]
    (get-in db flags-db-path)))


(re-frame/reg-sub
  ::form-flags
  :<- [::flags]
  (fn [flags [_ form-id]]
    (get flags form-id)))


(re-frame/reg-sub
  ::flag-value
  :<- [::flags]
  (fn [flags [_ form-id flag]]
    (get-in flags [form-id flag])))


(re-frame/reg-event-db
  ::set-flag-value
  (fn [db [_ form-id flag new-value]]
    (assoc-in db (conj flags-db-path form-id flag) new-value)))



(re-frame/reg-event-db
  ::clean-form-state
  (fn [db [_ form-id]]
    (-> db
        (update-in value-db-path dissoc form-id)
        (update-in flags-db-path dissoc form-id))))


(re-frame/reg-sub
  ::show-validation-errors?
  :<- [::values]
  :<- [::flags]
  (fn [[form-values flags] [_ form-id field-path field-spec]]
    (and (get-in flags [form-id ::initial-submit-dispatched?])
         (not (s/valid? field-spec (get-in form-values field-path))))))

