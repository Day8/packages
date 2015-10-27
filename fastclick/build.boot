(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.10" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.0.6-0")
(bootlaces! +version+)

(task-options!
 pom  {:project     'cljsjs/fastclick
       :version     +version+
       :description "Polyfill to remove click delays on browsers with touch UIs"
       :url         "https://github.com/ftlabs/fastclick"
       :scm         {:url "https://github.com/ftlabs/fastclick"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url "https://github.com/ftlabs/fastclick/archive/v1.0.6.zip"
              :checksum "E607A3ADC8C45423F16B912C94789C84"
              :unzip true)
    (sift :move {#"^fastclick-.*/lib/fastclick\.js" "cljsjs/fastclick/development/fastclick.inc.js"})
    (minify :in "cljsjs/fastclick/development/fastclick.inc.js"
            :out "cljsjs/fastclick/production/fastclick.min.inc.js")
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.fastclick")))
