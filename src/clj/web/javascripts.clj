(ns web.javascripts)

(defn react
  ([] [:script {:type "text/javascript" :src "/assets/javascripts/lib/react/0.12.1/react.js"}]))

(defn google-closure-dev
  ([] [:script {:type "text/javascript" :src "/javascripts/cljs-dev/goog/base.js"}]))

(defn google-closure-production
  ([] [:script {:type "text/javascript" :src "/javascripts/cljs-production/goog/base.js"}]))

(defn cljs-dev-build
  ([] [:script {:type "text/javascript" :src "/javascripts/dev.js"}]))

(defn cljs-production-build
  ([] [:script {:type "text/javascript" :src "/javascripts/production.js"}]))
