(ns re-frame-spec-forms.steps.ranges-input
  (:require [forms.controls :as form-controls]
            [forms.core :as forms]
            [re-frame-spec-forms.formatters :as formatters]
            [re-frame.core :as re-frame]

            [re-frame-spec-forms.utils :as utils]
            [re-frame-spec-forms.model :as model]
            [reagent.core :as reagent]))


(defn ranges-input [form-id]
  (let [ranges @(re-frame/subscribe [::forms/field-value form-id [:ranges]])
        {:keys [::forms/submitting?]} @(re-frame/subscribe [::forms/form-flags form-id])]

    [:div.ui.form
     [:div.required.field
      [:label "Ranges"]
      (->> (range (count ranges))
           (map (fn [i]
                  [:div.item
                   [:div.fields
                    [form-controls/number-input
                     form-id
                     [:ranges i :first]
                     ::model/first
                     "Invalid ASN"
                     {:field-classes ["six" "wide"]}]

                    [form-controls/number-input
                     form-id
                     [:ranges i :last]
                     ::model/first
                     "Invalid ASN"
                     {:field-classes ["six" "wide"]}]

                    [:div.four.wide.field
                     [:i.red.trash.link.icon
                      {:class    (when submitting? "disabled")
                       :on-click #(when-not submitting?
                                    (re-frame/dispatch [::forms/set-field-value form-id [:ranges]
                                                        (utils/vec-remove ranges i)]))}]]]]))

           (into [:div.items]))

      [:button.ui.mini.basic.teal.button
       {:class    (when submitting? "disabled")
        :on-click #(when-not submitting?
                     (re-frame/dispatch [::forms/set-field-value form-id [:ranges]
                                         (conj (or ranges []) {})]))}
       [:i.plus.circle.icon]
       " Add a range"]]]))


(defn main-panel []
  (let [form-id "ranges-input-field"]
    (reagent/create-class
      {:component-will-mount
       #(re-frame/dispatch [::forms/set-flag-value form-id ::forms/initial-submit-dispatched? true])

       :component-function
       (fn []
         [:div.ui.internally.celled.grid
          [:div.two.column.row
           [:div.column
            [:h4.ui.dividing.header "Rendered form"]
            [ranges-input form-id]]
           [:div.column
            [:h4.ui.dividing.header "Form value"]
            [formatters/inspect @(re-frame/subscribe [::forms/field-value form-id []]) true]]]])})))
