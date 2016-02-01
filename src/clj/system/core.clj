(ns system.core
  (:require [com.stuartsierra.component :as component]
            [system.components.http-server :as http-server]
            [controllers.dashboard :as dashboard]))

(def routes
  ["/" {:post
        {}

        :get
        {"" (fn [request] (dashboard/core request))}}])

(defn online-system
  ([] (online-system {}))
  ([{:keys
     [environment
      web-host web-port cookie-store-secret-key]
     :or {}
     :as options}]

   (component/system-map
    :http-server (http-server/new-component {:environment environment
                                             :web-host web-host
                                             :web-port web-port
                                             :jetty-options {:join? false}
                                             :cookie-store-secret-key cookie-store-secret-key
                                             :routes routes}))))
