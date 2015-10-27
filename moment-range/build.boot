(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.9" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0" :scope "test"]
                  [cljsjs/moment "2.9.0-3"]])

(require '[adzerk.bootlaces :refer :all]
         '[boot.task-helpers]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "2.0.3-0")
(bootlaces! +version+)

(task-options!
  push {:ensure-clean false}
  pom  {:project     'cljsjs/moment-range
        :version     +version+
        :description "A javascript date library for parsing, validating, manipulating, and formatting dates."
        :url         "http://gf3.github.io/moment-range/"
        :license     {"UNLICENSED" "http://unlicense.org/"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(require '[boot.core :as c]
         '[boot.tmpdir :as tmpd]
         '[clojure.java.io :as io]
         '[clojure.string :as string])

(deftask package []
  (comp
    (download :url "https://github.com/gf3/moment-range/archive/2.0.3.zip"
              :checksum "705f4467371c7c3a442d0dbc573fe073"
              :unzip true)

    (sift :move {#"^moment-range.*/dist/moment-range\.js"          "cljsjs/moment-range/development/moment-range.inc.js"
                 #"^moment-range.*/dist/moment-range\.min\.js"     "cljsjs/moment-range/production/moment-range.min.inc.js"})

    (sift :include #{#"^cljsjs"})

    (deps-cljs :name "cljsjs.moment-range"
               :requires #{"cljsjs.moment"})))
