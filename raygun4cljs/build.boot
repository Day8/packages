(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.10" :scope "test"]
                  [cljsjs/boot-cljsjs "0.4.6"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.18.3-0")
(bootlaces! +version+)

(task-options!
  pom  {:project     'raygun4cljs
        :version     +version+
        :description "Raygun.io plugin"
        :url         "https://github.com/MindscapeHQ/raygun4js"
        :license     {"MIT" "http://opensource.org/licenses/MIT"}
        :scm         {:url  "https://github.com/Day8/packages"}})

(deftask package []
  (comp
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.3/dist/raygun.js"
              :checksum "155c4c2c2a09288f5440506f22ee3c8d")
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.3/dist/raygun.min.js"
              :checksum "bc2cbcf40ea21db8b367bc1205779d6f")
    (sift :move {#"^raygun\.js"      "cljsjs/raygun4cljs/development/raygun.inc.js"
                 #"^raygun\.min\.js" "cljsjs/raygun4cljs/production/raygun.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "raygun4cljs")))
