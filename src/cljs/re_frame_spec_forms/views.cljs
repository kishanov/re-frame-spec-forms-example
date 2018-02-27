(ns re-frame-spec-forms.views
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.events :as events]
            [re-frame-spec-forms.subs :as subs]
            [re-frame-spec-forms.routes :as routes]

            [re-frame-spec-forms.backend-sim :as backend-sim]

            [re-frame-spec-forms.steps.layout]
            [re-frame-spec-forms.steps.spec-def]
            [re-frame-spec-forms.steps.label-input]
            [re-frame-spec-forms.steps.value-providers]
            [re-frame-spec-forms.steps.ranges-input]
            [re-frame-spec-forms.steps.state]
            [re-frame-spec-forms.formatters :as formatters]))


(defn landing []
  [:div.ui.internally.celled.grid
   [:div.two.column.row
    [:div.column
     [:h4.ui.dividing.header "About"]
     [:p "Welcome to the example application "]]
    [:div
     [:h4.ui.dividing.header "Sample payload"]
     [formatters/inspect backend-sim/sample-asn-pool]]]])



(defn steps [current-route-key]
  (->> (list [::routes/landing "Introduction" ""]
             [::routes/layout "Form layout" "Define form controls"]
             [::routes/spec-def "Data model using spec" "Describe payload using clojure.spec"]
             [::routes/label-input "First form control" "Define form control for label input"]
             [::routes/choice-providers "Choice form controls" "Show how form controls that require external chioces providers can be built"]
             [::routes/ranges-input "Complex form controls" "Form controls to manage collections and nested elements"]
             [::routes/form-state "Form state" "Show form behavior during it's lifecycle"])
       (map (fn [[route-key title desc]]
              [:div.step
               {:class    (when (= current-route-key route-key) "active")
                :style    {:cursor "pointer"}
                :on-click #(re-frame/dispatch [::events/change-route-key route-key])}
               [:div.content
                [:div.title title]
                [:div.description
                 {:style {:max-width "10em"}}
                 desc]]]))

       (into [:div.ui.vertical.ordered.steps])))



(defn main-panel []
  (let [route-key @(re-frame/subscribe [::subs/current-route-key])]
    [:div.ui.container
     {:style {:margin-top "5em"}}
     [:h1.ui.header "Form with re-frame and clojure.spec"]
     [:div.ui.grid
      [:div.two.column.row
       [:div.four.wide.column
        [steps route-key]]
       [:div.twelve.wide.column
        ^{:key route-key}
        [:div.section
         (condp = route-key
           ::routes/layout [re-frame-spec-forms.steps.layout/main-panel]
           ::routes/spec-def [re-frame-spec-forms.steps.spec-def/main-panel]
           ::routes/label-input [re-frame-spec-forms.steps.label-input/main-panel]
           ::routes/choice-providers [re-frame-spec-forms.steps.value-providers/main-panel]
           ::routes/ranges-input [re-frame-spec-forms.steps.ranges-input/main-panel]
           ::routes/form-state [re-frame-spec-forms.steps.state/main-panel]
           [landing])]]]]]))
