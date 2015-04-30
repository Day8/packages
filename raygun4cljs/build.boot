(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.10" :scope "test"]
                  [cljsjs/boot-cljsjs "0.4.6"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.18.2-1")
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
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.2/dist/raygun.js"
              :checksum "eeba8bed9fa2c9bf7efeee7692b80521")
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.2/dist/raygun.min.js"
              :checksum "44bb85c30135b91dc798bd4a89c61bf9")
    (sift :move {#"^raygun\.js"      "cljsjs/raygun4cljs/development/raygun.inc.js"
                 #"^raygun\.min\.js" "cljsjs/raygun4cljs/production/raygun.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "raygun4cljs")))
