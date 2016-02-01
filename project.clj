(defproject sketchpad "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]

                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/clojurescript "0.0-3308"]

                 [com.stuartsierra/component "0.3.0"]

                 [ring "1.3.2"]
                 [ring-basic-authentication "1.0.5"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-codec "1.0.0"]
                 [ring/ring-json "0.4.0"]
                 [jumblerg/ring.middleware.cors "1.0.1"]
                 [fogus/ring-edn "0.3.0"]
                 [prone "0.8.2"]

                 [http-kit "2.1.19"]
                 [hiccup "1.0.5"]
                 [hiccups "0.3.0"]
                 [hickory "0.5.4"]
                 [cheshire "5.5.0"]

                 [cljs-ajax "0.3.12"]
                 [com.andrewmcveigh/cljs-time "0.3.5"]

                 [reagent "0.5.0"]

                 [bidi "1.19.0"]
                 [cljsbuild "1.0.6"]]

  :source-paths ["src/clj"]

  :min-lein-version "2.0.0"

  :uberjar-name "sketchpad-standalone.jar"

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-figwheel "0.3.7"]]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel true
                        :compiler
                          {:optimizations :none
                           :output-to "resources/public/javascripts/dev.js"
                           :output-dir "resources/public/javascripts/cljs-dev/"
                           :pretty-print true
                           :source-map true}}

                       {:id "production"
                        :source-paths ["src/cljs"]
                        :compiler
                          {:optimizations :none
                           :output-to "resources/public/javascripts/production.js"
                           :output-dir "resources/public/javascripts/cljs-production/"
                           :pretty-print false
                           :source-map true}}]}

  :figwheel {:css-dirs ["resources/public/assets/stylesheets"]}

  :main application.main

  :aot [application.main]

  :global-vars {*print-length* 25}

  :repl-options {:init-ns dev.core})
