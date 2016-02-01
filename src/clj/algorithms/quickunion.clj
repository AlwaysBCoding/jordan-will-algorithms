(ns algorithms.quickunion)

(def start (atom (vec (range 10))))
(def size (atom (vec (take 10 (repeat 1)))))

(defn connected [x y]
  (= (root x) (root y)))

(defn root [x]
  (let [array @start]
    (loop [index x]
      (if (= index (nth array index))
        index
        (recur (nth array index))))))

(defn union [x y]
  (let [rootx (root x)
        rooty (root y)]
    (swap! start assoc rootx rooty)))

(defn weighted-union [x y]
  (let [rootx (root x)
        rooty (root y)]

    (if (not= rootx rooty)
      (if (< (nth @size x) (nth @size y))
        (do
          (swap! start assoc rootx rooty)
          (swap! size update rooty #(+ % (nth @size rootx))))
        (do
          (swap! start assoc rooty rootx)
          (swap! size update rootx #(+ % (nth @size rooty))))))))

(defn display []
  {:start @start
   :size @size})
