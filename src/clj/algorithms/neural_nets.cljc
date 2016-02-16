(ns algorithms.neural-nets
  (:require [clojure.core.matrix :as matrix]))

(defn sigmoid [x]
  (/ 1 (+ 1 (Math/pow (Math/exp 1) (* -1 x)))))

(defn dsigmoid [x]
  (* (sigmoid x) (- 1 (sigmoid x))))

(defprotocol Traversable
  (forward [this X]))

(defrecord NeuralNetwork [input-layer-size hidden-layer-size output-layer-size W1 W2]
  Traversable
  (forward [this X]
    #_(as-> X <>
        (matrix/mmul <> W1)
        (matrix/emap sigmoid <>)
        (matrix/mmul <> W2)
        (matrix/emap sigmoid <>))
    
    (let [Z2 (matrix/mmul X W1)
          A2 (matrix/emap sigmoid Z2)
          Z3 (matrix/mmul A2 W2)
          YHAT (matrix/emap sigmoid Z3)]
        YHAT)))

(defn create-neural-network [{:keys [input-layer-size hidden-layer-size output-layer-size]}]
  (let [W1 (->> (matrix/new-matrix input-layer-size hidden-layer-size)
                (matrix/emap (fn [x] (if (zero? (rand-int 2)) (* -1 (rand 5)) (rand 5)))))
        W2 (->> (matrix/new-matrix hidden-layer-size output-layer-size)
                (matrix/emap (fn [x] (if (zero? (rand-int 2)) (* -1 (rand 5)) (rand 5)))))]
    (map->NeuralNetwork {:input-layer-size input-layer-size
                         :hidden-layer-size hidden-layer-size
                         :output-layer-size output-layer-size
                         :W1 W1
                         :W2 W2})))
