(ns re-frame-spec-forms.views
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.events :as events]
            [re-frame-spec-forms.subs :as subs]
            [re-frame-spec-forms.routes :as routes]

            [re-frame-spec-forms.steps.layout]
            [re-frame-spec-forms.steps.spec-def]
            [re-frame-spec-forms.steps.label-input]
            [re-frame-spec-forms.steps.ranges-input]
            [re-frame-spec-forms.steps.state]))



(defn steps [current-route-key]
  (->> (list [::routes/landing "Intro" ""]
             [::routes/layout "Layout" "Define form controls"]
             [::routes/spec-def "Spec" "Describe payload using clojure.spec"]
             [::routes/label-input "Label input" "Define form control for label input"]
             [::routes/ranges-input "Ranges input" "Form controls to manage collection and nested elements"]
             [::routes/form-state "Form state" "Show form behavior during it's lifecycle"])
       (map (fn [[route-key title desc]]
              [:div.step
               {:class    (when (= current-route-key route-key) "active")
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
           ::routes/ranges-input [re-frame-spec-forms.steps.ranges-input/main-panel]
           ::routes/form-state [re-frame-spec-forms.steps.state/main-panel]
           [:pre [:code route-key]])]]]]]))
