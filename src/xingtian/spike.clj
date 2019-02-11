(ns xingtian.spike
  (:import (eu.mihosoft.jcsg Cube Sphere)
           (eu.mihosoft.vvecmath Transform)))

(def cube (.toCSG (new Cube 2)))
(def sphere (.toCSG (new Sphere 1.25)))

(def cube-plus-sphere (.union cube sphere))
(def cube-minus-sphere (.difference cube sphere))
(def cube-intersect-sphere (.intersect cube sphere))

(def union
  (let [sphere* (.transformed sphere (.. Transform
                                       (unity)
                                       (translateX 3)))
        cps     (.transformed cube-plus-sphere (.. Transform
                                                 (unity)
                                                 (translateX 6)))
        cms     (.transformed cube-minus-sphere (.. Transform
                                                  (unity)
                                                  (translateX 9)))
        cis     (.transformed cube-intersect-sphere (.. Transform
                                                      (unity)
                                                      (translateX 12)))
        ]
    (.. cube
      (union sphere*)
      (union cms)
      (union cis)
      (union cps))))

(spit "test.stl"
  (.toStlString union))
