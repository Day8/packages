(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.11" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.18.7-0")
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
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.7/dist/raygun.js"
              :checksum "bdedc83bacb64315c67535a36da3a024")
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.7/dist/raygun.min.js"
              :checksum "d68bf1c0f717e87bc5c5555eff4f179f")
    (sift :move {#"^raygun\.js"      "cljsjs/raygun4cljs/development/raygun.inc.js"
                 #"^raygun\.min\.js" "cljsjs/raygun4cljs/production/raygun.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "raygun4cljs")))
