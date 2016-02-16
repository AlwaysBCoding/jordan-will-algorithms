(ns cljs.dashboard.core
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [algorithms.percolation :as percolation]
            [cljs.core.async :refer (put! chan <!)]
            [reagent.core :as r]
            [cljs.helpers.utils :as utils]))

;; Config
(def CX (chan))
(def CANVAS_SIZE 500)

;; State
(def app-state
  (r/atom {:message "Hello World!"}))

;; Event Helpers
(defn draw-canvas [{:keys [ctx grid]}]
  (let [roots (:roots grid)
        sizes (:sizes grid)]
    (.clearRect ctx 0 0 CANVAS_SIZE CANVAS_SIZE)
    (js/console.log (pr-str grid))))

;; Events
(def events
  {:run-simulation
   (fn [{:keys [grid-size]}]
     (let [canvas (js/document.getElementById "simulation-canvas")
           ctx (.getContext canvas "2d")
           grid (percolation/simulation grid-size)]
       (draw-canvas {:ctx ctx :grid grid})))})

;; Components

;; App Component
(defn app-component []
  [:div#cljs-app
   [:h1 (:message @app-state)]
   [:div.simulation-actions
    [:button
     {:on-click (fn [event] (put! CX [[:run-simulation] {:grid-size 3}]))}
     "Run Simulation"]]
   [:div.simulation
    [:canvas#simulation-canvas
     {:height CANVAS_SIZE
      :width CANVAS_SIZE}]]])

;; Start App
(r/render [app-component] (js/document.querySelector "#cljs-target"))

;; Event Loop
(go
  (while true
    (let [[event-path event-data] (<! CX)]
      (if-let [event-handler (get-in events event-path)]
        (event-handler event-data)
        (js.console.warn (str "No handler for event path " event-path))))))

;; Runners
(js/console.log (pr-str (percolation/simulation 3)))
#_(js/console.log (percolation/simulations 15 15))
