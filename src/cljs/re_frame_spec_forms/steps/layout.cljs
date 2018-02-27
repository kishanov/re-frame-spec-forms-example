(ns re-frame-spec-forms.steps.layout
  (:require [re-frame-spec-forms.formatters :as formatters]
            [cljs.repl :as r]))


(defn form-layout []
  [:div.ui.form
   [:div.required.field
    [:label "Name"]
    [:input {:type "text"}]]

   [:div.inline.field
    [:div.ui.checkbox
     [:input {:type "checkbox"}]
     [:label "Preallocated?"]]]

   (->> ["multihomed" "stub" "transit" "ixp"]
        (map (fn [type]
               [:div.field
                [:div.ui.radio.checkbox
                 [:input {:type "radio" :name type}]
                 [:label type]]]))
        (into [:div.inline.fields
               [:label "Type"]]))

   [:div.field
    [:label "Tags"]
    [:input {:type "text"}]]

   [:div.required.field
    [:label "Ranges"]
    [:div.ui.items
     [:div.item
      [:div.inline.fields
       [:div.four.wide.field
        [:input {:type "number" :placeholder "first"}]]
       [:div.four.wide.field
        [:input {:type "number" :placeholder "last"}]]
       [:div.two.wide.field
        [:i.red.trash.icon]]]]]

    [:button.ui.mini.basic.teal.button
     [:i.plus.circle.icon]
     " Add a range"]]])


(defn main-panel []
  [:div.ui.internally.celled.grid
   [:div.two.column.row
    [:div.column
     [:h4.ui.dividing.header "Rendered form"]
     [form-layout]]
    [:div.column
     [:h4.ui.dividing.header "Source code"]
     [:pre
      [:code {:class "clojure"}
       (with-out-str (r/source form-layout))]]]]])
