(ns application.main
  (:require [com.stuartsierra.component :as component]
            [system.core :as system]))

(defn -main [& args]

  (if (first args)

    ;; WEB PORT IS INJECTED
    (let [config-map {:environment "development"
                      :web-host "localhost"
                      :web-port (Integer. (first args))
                      :cookie-store-secret-key ""}
          init-system (system/online-system config-map)
          start-system (component/start init-system)]

      (println (str "System Started on PORT: " (:web-port config-map))))
    
    ;; MAKE WEB PORT 3018
    (let [config-map {:environment "development"
                      :web-host "localhost"
                      :web-port 3018
                      :cookie-store-secret-key ""}
          init-system (system/online-system config-map)
          start-system (component/start init-system)]

      (println (str "System Started on PORT: " (:web-port config-map))))))

