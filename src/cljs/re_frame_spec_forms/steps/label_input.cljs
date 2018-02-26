(ns re-frame-spec-forms.steps.label-input
  (:require [re-frame.core :as re-frame]
            [forms.core :as forms]
            [forms.controls :as form-controls]

            [re-frame-spec-forms.formatters :as formatters]

            [re-frame-spec-forms.subs :as subs]
            [re-frame-spec-forms.model :as model]
            [cljs.spec.alpha :as s]
            [reagent.core :as reagent]))


(defn label-input [form-id]
  [:div.ui.form
   [form-controls/text-input
    form-id
    [:label]
    ::model/label
    "Label should be a string 1..64 chars long"
    {:field-classes ["required"]
     :label         "Label"}]])


(defn main-panel []
  (let [form-id "label-input-field"]
    (reagent/create-class
      {:component-will-mount
       #(re-frame/dispatch [::forms/set-flag-value form-id ::forms/initial-submit-dispatched? true])

       :component-function
       (fn []
         [:div.ui.internally.celled.grid
          [:div.two.column.row
           [:div.column
            [:h4.ui.dividing.header "Rendered form"]
            [label-input form-id]]
           [:div.column
            [:h4.ui.dividing.header "Form value"]
            [formatters/inspect @(re-frame/subscribe [::forms/field-value form-id []]) true]]]])})))
