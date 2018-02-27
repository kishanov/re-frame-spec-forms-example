(ns re-frame-spec-forms.subs
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.backend-sim :as back-end]
            [re-frame-spec-forms.db :as db]))


(re-frame/reg-sub
  ::current-route-key
  (fn [db]
    (::db/current-route-key db)))


(re-frame/reg-sub
  ::asn-types-api-response
  (constantly back-end/asn-types))