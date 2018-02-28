(ns forms.controls
  (:require [re-frame.core :as re-frame]
            [forms.core :as forms]
            [cljs.spec.alpha :as s]))


(defn input [type coerce-fn form-id field-path field-spec validation-error-msg
             & [{:keys [label field-classes attrs] :as options}]]
  (let [field-value @(re-frame/subscribe [::forms/field-value form-id field-path])
        show-validation-errors? @(re-frame/subscribe [::forms/show-validation-errors? form-id field-path field-spec])
        {:keys [::forms/submitting?]} @(re-frame/subscribe [::forms/form-flags form-id])
        field-valid? (s/valid? field-spec field-value)
        show-errors? (and (not field-valid?) show-validation-errors?)]

    [:div.required.field
     {:class (cond->> (or field-classes (list))
                      show-errors? (cons "error")
                      submitting? (cons "disabled")
                      true (clojure.string/join \space))}

     (when label
       [:label "Label"])

     [:input
      (merge
        attrs
        {:type      type
         :value     field-value
         :on-change #(re-frame/dispatch [::forms/set-field-value form-id field-path (coerce-fn (-> % .-target .-value))])})]

     (when show-errors?
       [:div.ui.pointing.red.basic.label validation-error-msg])]))


(def text-input (partial input "text" identity))
(def number-input (partial input "number" int))
