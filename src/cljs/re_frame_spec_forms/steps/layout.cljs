(ns re-frame-spec-forms.steps.layout)


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
    [:table.ui.table
     [:thead
      [:tr
       [:th.seven.wide "First"]
       [:th.seven.wide "Last"]
       [:th.two.wide]]]
     [:tbody
      [:tr
       [:td [:input {:type "number"}]]
       [:td [:input {:type "number"}]]
       [:td [:i.red.trash.icon]]]]
     [:tfoot
      [:tr
       [:th {:col-span 3}
        [:button.ui.mini.teal.button
         [:i.plus.circle.icon]
         " Add a range"]]]]]]])


(defn main-panel []
  [:div.ui.grid
   [:div.one.column.row
    [:div.eight.wide.column
     [form-layout]]]])
