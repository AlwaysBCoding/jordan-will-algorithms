(ns algorithms.percolation)

(def traversals (atom 0))

(defn generate-grid [n]
  {:roots (mapv (fn [n] nil) (range (* n n)))
   :sizes (mapv (fn [n] 1) (range (* n n)))})

(defn root [{:keys [roots] :as grid} x]
  (if-not (nil? x)
    (condp = (nth roots x)
      nil nil
      (do
        (loop [index x]
          (swap! traversals inc)
          (if (= index (nth roots index))
            index
            (recur (nth roots index))))))))

(defn connected? [{:keys [roots] :as grid} x y]
  (if-not (or (nil? x) (nil? y))
    (= (root grid x) (root grid y))
    false))

(defn neighbors [{:keys [roots] :as grid} x]
  (let [n (int #?(:clj (Math/sqrt (count roots))
                  :cljs (js/Math.sqrt (count roots))))]
    (vec (remove nil? [(if (> 0 (- x n)) nil (- x n)) ;; TOP
                       (if (<= (* n n) (+ x n)) nil (+ x n)) ;; BOTTOM
                       (if (= 0 (mod x n)) nil (- x 1)) ;; LEFT
                       (if (= (- n 1) (mod x n)) nil (+ x 1)) ;; RIGHT
                       ]))))

(defn connect [{:keys [roots] :as grid} x y]
  (let [new-grid (assoc-in grid [:roots (root grid x)] y)]
    (update-in new-grid [:sizes y] #(+ % (get-in new-grid [:sizes x])))))

(defn flip-square [{:keys [roots] :as grid} x]
  (loop [grid (assoc-in grid [:roots x] x)
         neighbors-to-connect (remove #(is-closed? grid %) (neighbors grid x))]
    (if (empty? neighbors-to-connect)
      grid
      (let [target-neighbor (first neighbors-to-connect)
            new-grid (connect grid target-neighbor x)
            new-neighbors (drop 1 neighbors-to-connect)]
        (recur
         new-grid
         new-neighbors)))))

(defn is-closed? [{:keys [roots] :as grid} x]
  (nil? (nth roots x)))

(defn percolates? [{:keys [roots] :as grid}]
  (let [n (int #?(:clj (Math/sqrt (count roots))
                  :cljs (js/Math.sqrt (count roots))))
        top-row (take n roots)
        bottom-row (take-last n roots)]

    (let [connections (->> top-row
                           (map (fn [top-index]
                                  (map (fn [bottom-index]
                                         (connected? grid top-index bottom-index)) bottom-row)))
                           (flatten))]

      (if (some true? connections) true false))))

(defn closed-squares [{:keys [roots] :as grid}]
  (->> roots
       (map-indexed vector)
       (filter (fn [square] (is-closed? grid (first square))))
       (mapv first)))

(defn simulation [grid-size]
  (loop [grid (generate-grid grid-size)]
    (if (percolates? grid)
      grid
      #_(double (/ (- (count (:roots grid)) (count (closed-squares grid))) (count (:roots grid))))
      (recur (flip-square grid (rand-nth (closed-squares grid)))))))

(defn simulations [trials grid-size]
  (double (/ (reduce (fn [memo i] (+ memo (simulation grid-size))) 0 (range trials)) trials)))
