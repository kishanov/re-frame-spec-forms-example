(ns re-frame-spec-forms.steps.value-providers
  (:require [cljs.repl :as r]
            [re-frame-spec-forms.subs :as subs]
            [forms.core :as forms]
            [re-frame.core :as re-frame]
            [re-frame-spec-forms.formatters :as formatters]))


(defn types-radio-input [form-id]
  (let [field-path [:type]
        field-value @(re-frame/subscribe [::forms/field-value form-id field-path])
        {:keys [::forms/submitting?]} @(re-frame/subscribe [::forms/form-flags form-id])]
    (into [:div.ui.grouped.fields
           [:label "Type"]]
          (map (fn [{:keys [label value]}]
                 [:div.field
                  {:class (when submitting? "disabled")}
                  [:div.ui.radio.checkbox
                   [:input
                    {:type      "radio"
                     :checked   (= value field-value)
                     :on-change #(when (-> % .-target .-checked)
                                   (re-frame/dispatch [::forms/set-field-value form-id field-path value]))}]
                   [:label label]]])
               @(re-frame/subscribe [::subs/asn-types-api-response])))))


(defn types-select-input [form-id]
  (let [field-path [:type]
        field-value @(re-frame/subscribe [::forms/field-value form-id field-path])]
    (into [:select.ui.dropdown
           {:value     field-value
            :on-change #(re-frame/dispatch [::forms/set-field-value form-id field-path (-> % .-target .-value)])}]
          (map (fn [{:keys [label value]}]
                 [:option
                  {:value value}
                  label])
               @(re-frame/subscribe [::subs/asn-types-api-response])))))


(defn form-layout [form-id]
  [:div.ui.form
   [types-radio-input form-id]
   [:div.ui.divider]
   [types-select-input form-id]])



(defn main-panel []
  (let [form-id "choices-provider-example"]
    [:div.ui.internally.celled.grid
     [:div.two.column.row
      [:div.column
       [:h4.ui.dividing.header "Rendered form"]
       [form-layout form-id]]
      [:div.column
       [:h4.ui.dividing.header "Form value"]
       [formatters/inspect @(re-frame/subscribe [::forms/field-value form-id []]) true]]]]))
