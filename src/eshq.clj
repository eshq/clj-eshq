(ns eshq
  (:refer-clojure :exclude [send key])
  (:require [clj-http.client :as http]
            [digest :as digest]))

(def url    (System/getenv "ESHQ_URL"))
(def key    (System/getenv "ESHQ_KEY"))
(def secret (System/getenv "ESHQ_SECRET"))

;; Private methods
(defn- token [time]
  (digest/sha-1 (clojure.string/join ":" [key secret time])))

(defn- credentials []
  (let [time (int (/ (System/currentTimeMillis) 1000))]
    {:key key :timestamp time :token (token time)}))

(defn- url-for [path] (clojure.string/join [url path]))

(defn- post [path params]
  (http/post (url-for path)
      {:form-params (merge params (credentials)) :as :json}))

;; API Methods
(defn open [params]
  "Used to open new connections to a channel. Returns a token that can be used
   to open an EventSource connection to the channel. Example:
   (eshq/open {:channel \"test\"})"
  (let [resp (post "/socket" params)]
    (if (= (resp :status) 200)
      (get-in resp [:body :socket])
      nil)))

(defn send [params]
  "Send a message to a channel. Example:
   (eshq/send {:channel \"test\" :data \"Hello\"})"
  (let [resp (post "/event" params)]
    (if (= (resp :status) 200)
      true
      false)))
