;;   Copyright (c) Rich Hickey and contributors. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;;   which can be found in the file epl-v10.html at the root of this distribution.
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any other, from this software.

(ns ^{:skip-wiki true}
  clojure.core.async.impl.dispatch
  (:require [clojure.core.async.impl.protocols :as impl]
            [clojure.core.async.impl.exec.threadpool :as tp]))

(set! *warn-on-reflection* true)

(defonce executor (delay (tp/thread-pool-executor)))
(defonce executor1 (delay (tp/thread-pool-executor "blocking-thread-%d")))

(defn run
  "Runs Runnable r in a thread pool thread"
  ([^Runnable r]
   (run r executor))
  ([^Runnable r executor_]
   (if executor_
     (impl/exec @executor_ r)
     (impl/exec @executor r))))

(defn run-blocking
  [^Runnable r]
  (run r executor1))
