(ns controllers.dashboard
  (:require [views.dashboard :as dashboard]))

(defn core [{:keys [] :as request}]
  {:status 200
   :headers {}
   :body (dashboard/core {})})
