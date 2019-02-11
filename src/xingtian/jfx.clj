(ns xingtian.jfx
  (:import (eu.mihosoft.jcsg Cube STL)
           (javafx.application Application)
           (java.nio.file Paths)
           (javafx.scene.shape Sphere)
           (javafx.scene.transform Rotate Scale Translate)
           (javafx.scene Scene Group PerspectiveCamera)))

(def cube (.. (new Cube 2)
            (toCSG)
            (toJavaFXMesh)
            (getAsMeshViews)))

(def sphere (new Sphere 100))

(def root (new Group))

(doto (.getChildren root)
  (.add sphere))

(doseq [tri cube]
  (doto (.getChildren root)
    (.add tri)))

(def camera (doto (new PerspectiveCamera true)
              (.setNearClip 0.1)
              (.setFarClip 10000.0)
              (.setTranslateZ -450)))

(def scene (new Scene root 1024 768))

(def p (proxy [Application] []
              (start [primary-stage]
                (println "hello world"))
              (main [& args]
                (Application/launch args))))
