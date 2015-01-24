(ns monad-play.core
  (:gen-class))

;; Inventing monads in clojure

;; 2 "debuggable" functions
(defn f [i] [i "f was called"] )
(defn g [i] [i "g was called"] )

;; A function for composing two debuggable functions 
(defn bind [f g] 
  (fn [i] 
    (let [[fir fis] (f i)
          [gir gis] (g fir)]
      [gir (str fis gis)])))

;; The composition of two debuggable functions f and g
(def h (bind f g))
(= (h 4) [4 "f was calledg was called"])

;; Identity function
(defn unit [x] [x ""])

;; Check has unit acts as identity
(= ((bind g unit) 1) ((bind unit g) 1))

;; Lift is a function that turns any function into a debuggable one
(defn lift [f] (fn [i] [(f i) ""]))

;; Sample function to lift
(defn square [i] (* i i))
(def lifted-square (lift square))
(= (lifted-square 2) [4 ""])

;; Compose f,g and square using bind and lift
(def comb (bind g (bind (lift square) f)))
(= (comb 3) [9 "g was calledf was called"])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
