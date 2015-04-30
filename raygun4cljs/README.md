# cljsjs/raygun4cljs

[](dependency)
```clojure
[org.clojars.day8/raygun4cljs "1.18.2-0"] ;; latest release
```
[](/dependency)

This jar comes with `deps.cljs` as used by the [Foreign Libs][flibs] feature
of the Clojurescript compiler. After adding the above dependency to your project
you can require the packaged library like so:

```clojure
(ns application.core
  (:require cljsjs.raygun4cljs :as raygun4cljs))
```
# Usage
```clojure
(defn register-raygun-session
  "Register session with raygun.io providing our app version, a unique session identifier and username.
   This is typically called each time your SPA initialises."
  [version session-id username]
  (.setVersion raygun version)
  (.setUser raygun username false nil nil nil (str site ":" username))
  (.init
    raygun
    raygun-api-key
    (clj->js {"debugMode" true "disableAnonymousUserTracking" true})
    (clj->js {"environment" "development"
              "session"     session-id
              ;other custom key value pairs allowed here see raygun documentation.})))
```
[flibs]: https://github.com/clojure/clojurescript/wiki/Foreign-Dependencies