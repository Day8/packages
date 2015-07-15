(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.11" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.18.4-0")
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
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.4/dist/raygun.js"
              :checksum "ece4bfee9a3b7ae2225b05145421f7a2")
    (download :url "https://raw.githubusercontent.com/MindscapeHQ/raygun4js/v1.18.4/dist/raygun.min.js"
              :checksum "0e94966a1cc2131125110707e6a70c71")
    (sift :move {#"^raygun\.js"      "cljsjs/raygun4cljs/development/raygun.inc.js"
                 #"^raygun\.min\.js" "cljsjs/raygun4cljs/production/raygun.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "raygun4cljs")))
