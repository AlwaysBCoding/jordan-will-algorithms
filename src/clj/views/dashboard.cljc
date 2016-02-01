(ns views.dashboard
  (:require [hiccup.page :as hiccup]
            [web.stylesheets :as css]
            [web.javascripts :as js]))

(defn core [{:keys [] :or {} :as options}]
  (let [header-content (atom [:head])
        footer-content (atom [:div.footer-javascripts])]
    
    ;; HEADER CONTENT
    (swap! header-content conj [:title "Algorithms Dashboard"])
    (swap! header-content into [[:link {:rel "stylesheet" :type "text/css" :href "https://fonts.googleapis.com/css?family=Raleway:400,500,600,700,300"}]
                                [:link {:rel "stylesheet" :type "text/css" :href "/assets/stylesheets/dashboard/core.css"}]])

    ;; FOOTER CONTENT
    (swap! footer-content into [(js/react)
                                (js/google-closure-dev)
                                (js/cljs-dev-build)])
    (swap! footer-content conj [:script {:type "text/javascript"} "goog.require('cljs.dashboard.core')"])

    ;; RENDER
    (hiccup/html5
     @header-content
     [:body
      [:div#cljs-target]
      @footer-content])))
