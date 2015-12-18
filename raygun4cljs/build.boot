(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.11" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "2.1.1-0")
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
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v2.1.1/dist/raygun.js"
              :checksum "874f1045edc9c9433a0e24495917d007")
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v2.1.1/dist/raygun.min.js"
              :checksum "5c9945ec793031bf855c9d311fd37893")
    (sift :move {#"^raygun\.js"      "cljsjs/raygun4cljs/development/raygun.inc.js"
                 #"^raygun\.min\.js" "cljsjs/raygun4cljs/production/raygun.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "raygun4cljs")))
