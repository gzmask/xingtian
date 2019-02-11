(ns xingtian.spike
  (:import (eu.mihosoft.jcsg Cube Sphere STL)
           (eu.mihosoft.vvecmath Transform)
           (java.nio.file Paths)))

;; test basic forms
(def cube (.toCSG (new Cube 2)))
(def sphere (.toCSG (new Sphere 1.25)))
(def figure* (STL/file (Paths/get "./figure.stl" (into-array [""]))))
(def figure
  (.transformed figure* (.. Transform
                          (unity)
                          (scale 0.1 0.1 0.1))))
;(spit "test.stl" (.toStlString figure))

;; test basic booleans
(def cube-plus-sphere (.union cube sphere))
(def cube-minus-sphere (.difference cube sphere))
(def cube-intersect-sphere (.intersect cube sphere))
(def cube-plus-figure (.union cube figure))
;(spit "test.stl" (.toStlString cube-plus-figure))

;; test large union
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
        cpf     (.transformed cube-plus-figure (.. Transform
                                                      (unity)
                                                      (translateX 15)))]
    (.. cube
      (union sphere*)
      (union cms)
      (union cis)
      (union cpf)
      (union cps))))

;(spit "test.stl" (.toStlString union))

;; test JavaFX
;; find out more about Java object with ,dv cider inpector
;; cube.toJavaFXMesh().getAsMeshViews() -> eu.mihosoft.jcsg.MeshContainer.getAsMeshViews
;; (System/getProperties) -> Oracle JDK 1.8 is required

;;(first (.getAsMeshViews (.toJavaFXMesh cube))) ;; of type javafx.scene.shape.MeshView

