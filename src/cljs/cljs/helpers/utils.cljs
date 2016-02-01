(ns cljs.helpers.utils
  (:require-macros [hiccups.core :as hiccups])
  (:require [cljs.reader :as cljs-reader]
            [ajax.core :as ajax]
            [goog.dom.classes :as classes]
            [hiccups.runtime :as hiccupsrt]))

(defn clj->html [clj]
  (hiccups/html clj))

(defn clj->json [clj]
  (.stringify js/JSON (clj->js clj)))

(defn read-string [string]
  (cljs-reader/read-string string))

(defn GET [request]
  (ajax/GET (:uri request) (dissoc request :uri)))

(defn POST [request]
  (ajax/POST (:uri request) (dissoc request :uri)))

(defn PUT [request]
  (ajax/PUT (:uri request) (dissoc request :uri)))

(defn DELETE [request]
  (ajax/DELETE (:uri request) (dissoc request :uri)))

(defn contains-substring? [string substring]
  (> (.indexOf (.toLowerCase (str string)) (.toLowerCase (str substring))) -1))

(defn replace-value [coll old-value new-value])

(defn add-class [element class-name]
  (classes/add element class-name))

(defn remove-class [element class-name]
  (classes/remove element class-name))

(defn tokenize [text]
  (->>
   (-> text
       (clojure.string/replace #"\(|\)" "")
       (clojure.string/replace #"-" " ")
       (clojure.string/split #" "))
   (remove empty?)
   (mapv #(.toLowerCase %))))

(defn keyword->string [a-key & optional]
  (str (clojure.string/join " " (clojure.string/split (name a-key) #"-")) (apply str optional) " "))

(defn contains-tokens? [target-text search-text]
  (every? (fn [search-token]
            (some true? (map (fn [target-token]
                               (contains-substring? target-token search-token)) (tokenize target-text)))) (tokenize search-text)))

(defn next-item [collection search-item]
  (let [indexed-collection (map-indexed (fn [index item] {:index index :item item}) collection)
        match-index (-> (filter #(= (:item %) search-item) indexed-collection)
                        (first)
                        (:index))]
    (if (= match-index (dec (count collection)))
      search-item
      (nth collection (inc match-index)))))

(defn previous-item [collection search-item]
  (let [indexed-collection (map-indexed (fn [index item] {:index index :item item}) collection)
        match-index (-> (filter #(= (:item %) search-item) indexed-collection)
                        (first)
                        (:index))]
    (if (= match-index 0)
      search-item
      (nth collection (dec match-index)))))

(defn get-index [collection search-item]
  (let [indexed-collection (map-indexed (fn [index item] {:index index :item item}) collection)]
    (if-let [match-index (-> (filter #(= (:item %) search-item) indexed-collection)
                             (first)
                             (:index))]
      match-index nil)))

(defn pluralized-count-string [count string]
  (case count
    nil (str 0 " " string "s")
    -1 (str count " " string)
    1 (str count " " string)
    (str count " " string "s")))

(defn pluralized-count [count string]
  (case count
    nil (str 0 " " string "s")
    -1 string
    1 string
    (str string "s")))

;; String Utils

(defn upcase
  "Converts the entire string to upper case"
  [#^String input-string]
  (. input-string toUpperCase))

(defn str-join
  "Returns a string of all elements in 'sequence', separated by
  'separator'.  Like Perl's 'join'."
  [separator sequence]
  (apply str (interpose separator sequence)))

(defn downcase
  "Converts the entire string to lower case"
  [#^String input-string]
  (. input-string toLowerCase))

(defn str-rest
  "Returns the rest of the string"
  [#^String input-string]
  (apply str (rest input-string)))

(defn capitalize
  "This method turns a string into a capitalized version, Xxxx"
  [#^String input-string]
  (str-join "" (list
		(upcase (str (first input-string)))
		(downcase (str-rest input-string)))))

(defn titleize
  "This method takes an input string, splits it across whitespace, dashes, and underscores.  Each word is capitalized, and the result is joined with \" \"."
  [#^String input-string]
  (let [words (clojure.string/split input-string #"[\s_-]+")]
    (str-join " " (map capitalize words))))
