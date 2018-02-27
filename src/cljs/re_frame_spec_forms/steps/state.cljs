(ns re-frame-spec-forms.steps.state
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]

            [re-frame-spec-forms.formatters :as formatters]
            [forms.core :as forms]
            [re-frame-spec-forms.steps.label-input :as label-input]
            [re-frame-spec-forms.steps.ranges-input :as ranges-input]

            [re-frame-spec-forms.model :as model]
            [re-frame-spec-forms.events :as events]))


(defn form-state-wrapper [form-id]
  (reagent/create-class
    {:component-will-mount
     #(re-frame/dispatch [::forms/set-field-value form-id []
                          {:ranges [{}]}])

     :component-will-unmount
     #(re-frame/dispatch [::forms/clean-form-state form-id])

     :component-function
     (fn [form-id]
       (let [{:keys [::forms/initial-submit-dispatched?
                     ::forms/submitting?]} @(re-frame/subscribe [::forms/form-flags form-id])]
         [:div.ui.form
          [:div.ui.section
           [label-input/label-input form-id]
           [ranges-input/ranges-input form-id]]

          [:div.ui.hidden.divider]

          [:div.ui.center.aligned.section
           [:button.ui.primary.button
            {:on-click (fn []
                         (re-frame/dispatch [::forms/set-flag-value form-id ::forms/initial-submit-dispatched? true])
                         (re-frame/dispatch [::events/submit-form form-id]))}
            "Submit"]]]))}))


(defn main-panel []
  (let [form-id "form-state-wrapper"]
    [:div.ui.internally.celled.grid
     [:div.two.column.row
      [:div.column
       [:h4.ui.dividing.header "Rendered form"]
       [form-state-wrapper form-id]]
      [:div.column
       [:h4.ui.dividing.header "Source code"]
       [formatters/inspect @(re-frame/subscribe [::forms/field-value form-id []])]]]]))