(ns re-frame-spec-forms.views
  (:require [re-frame.core :as re-frame]
            [re-frame-spec-forms.events :as events]
            [re-frame-spec-forms.subs :as subs]
            [re-frame-spec-forms.routes :as routes]

            [re-frame-spec-forms.steps.layout :as layout]))



(defn steps [current-route-key]
  (->> (list [::routes/landing "Intro" ""]
             [::routes/_01-layout "Layout" "Define form controls"])
       (map (fn [[route-key title desc]]
              [:div.step
               {:class    (when (= current-route-key route-key) "active")
                :on-click #(re-frame/dispatch [::events/change-route-key route-key])}
               [:div.content
                [:div.title title]
                [:div.description desc]]]))

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
        [:div.section
         (condp = route-key
           ::routes/_01-layout [layout/main-panel]
           [:pre [:code route-key]])]]]]]))
