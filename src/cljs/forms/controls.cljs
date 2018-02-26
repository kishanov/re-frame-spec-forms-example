(ns forms.controls
  (:require [re-frame.core :as re-frame]
            [forms.core :as forms]
            [cljs.spec.alpha :as s]))


(defn text-input [form-id field-path field-spec validation-error-msg
                  & {:keys [label field-classes] :as options}]
  (let [field-value @(re-frame/subscribe [::forms/field-value form-id field-path])
        show-validation-errors? @(re-frame/subscribe [::forms/show-validation-errors? form-id field-path field-spec])
        field-valid? (s/valid? field-spec field-value)
        show-errors? (and (not field-valid?) show-validation-errors?)]

    [:div.required.field
     {:class (cond->> (or field-classes (list))
                      show-errors? (cons "error")
                      true (clojure.string/join \space))}

     (when label
       [:label "Label"])

     [:input
      {:type      "text"
       :value     field-value
       :on-change #(re-frame/dispatch [::forms/set-field-value form-id field-path (-> % .-target .-value)])}]

     (when show-errors?
       [:div.ui.pointing.red.basic.label validation-error-msg])]))
