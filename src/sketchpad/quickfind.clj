(ns sketchpad.quickfind)

(def start (atom (vec (range 10))))

(defn connected [x y]
  (let [array @start]
    (= (nth array x) (nth array y))))

(defn union [x y]
  (let [array @start
        xid (nth array x)
        yid (nth array y)]

    (doseq [[index n] (map-indexed vector array)]
      (if (= n xid)
        (swap! start assoc index yid)))

    @start))

