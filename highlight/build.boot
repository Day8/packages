(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.9" :scope "test"]
                  [cljsjs/boot-cljsjs "0.4.6" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "8.4-0")

(task-options!
  pom  {:project     'cljsjs/highlight
        :version     +version+
        :scm         {:url "https://github.com/cljsjs/packages"}
        :description "highlight.js packaged up with Google Closure externs"
        :url         "https://highlightjs.org/"
        :license     {"BSD" "http://opensource.org/licenses/BSD-3-Clause"}
        })

(require '[boot.core :as c]
         '[boot.tmpdir :as tmpd]
         '[clojure.java.io :as io]
         '[clojure.string :as string])

(deftask generate-lang-deps []
  (let [tmp (c/temp-dir!)
        new-deps-file (io/file tmp "deps.cljs")
        path->lang (fn [path] (second (re-matches #"cljsjs/common/languages/(.*)\.inc\.js" path)))
        lang->foreign-lib (fn [lang]
                              {:file     (str "cljsjs/common/languages/" lang ".inc.js")
                               :requires ["cljsjs.highlight"]
                               :provides [(str "cljsjs.highlight.langs." lang)]})]
    (with-pre-wrap
      fileset
      (let [existing-deps-file (->> fileset c/input-files (c/by-name ["deps.cljs"]) first)
            existing-deps      (-> existing-deps-file tmpd/file slurp read-string)
            lang-files         (->> fileset c/input-files (c/by-re [#"^cljsjs/common/languages/.*\.inc\.js"]))
            langs              (map (comp lang->foreign-lib path->lang tmpd/path) lang-files)
            new-deps           (update-in existing-deps [:foreign-libs] concat langs)]
        (spit new-deps-file (pr-str new-deps))
        (-> fileset (c/add-resource tmp) c/commit!)))))

(deftask package []
  (comp

    (sift :move {#"cljsjs/common/highlight\.min\.js" "cljsjs/common/highlight.inc.js"})
    (deps-cljs :name "cljsjs.highlight")

    (sift :move {#"cljsjs/common/languages/(.*)\.min\.js" "cljsjs/common/languages/$1.inc.js"})

    (generate-lang-deps)))
