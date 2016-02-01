(ns cljs.dashboard.core
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [algorithms.percolation :as percolation]
            [cljs.core.async :refer (put! chan <!)]
            [reagent.core :as r]
            [cljs.helpers.utils :as utils]))

;; Config
(def CX (chan))

;; State
(def app-state
  (r/atom {:message "Hello World!"}))

;; Event Helpers

;; Events
(def events
  {})

;; Components

;; App Component
(defn app-component []
  [:div#cljs-app
   [:h1 (:message @app-state)]])

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
#_(js/console.log (percolation/simulations 15 15))
