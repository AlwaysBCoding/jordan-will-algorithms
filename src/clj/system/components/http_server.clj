(ns system.components.http-server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer (wrap-resource)]
            [bidi.ring :refer (make-handler)]))

(defn development-handler [this]
  (-> (make-handler (:routes this))
      (wrap-resource "public")))

(defn production-handler [this]
  (-> (make-handler (:routes this))
      (wrap-resource "public")))

(defrecord HTTPServer [environment host port jetty-options cookie-store-secret-key routes]
  component/Lifecycle

  (start [this]
    (println "TRYING TO START THE SERVER...")
    (let [server (cond
                   (= environment "development") (jetty/run-jetty (development-handler this) (merge {:host host :port port :join? false} jetty-options))
                   (= environment "production") (jetty/run-jetty (production-handler this) (merge {:host host :port port :joun? false} jetty-options)))]
      (assoc this :server server)))

  
  (stop [this]
    (if-let [server (:server this)] (.stop server))
    (dissoc this :server)))

(defn new-component
  [{:keys [environment web-host web-port jetty-options cookie-store-secret-key routes]}]
  (map->HTTPServer {:environment environment
                    :host web-host
                    :port web-port
                    :jetty-options jetty-options
                    :cookie-store-secret-key cookie-store-secret-key
                    :routes routes}))
