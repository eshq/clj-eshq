## The EventSource HQ Clojure Client

Make sure you have an EventSource HQ account.

The endpoint and authentication details will be picked up from the
following environment variables:

    ESHQ_URL
    ESHQ_KEY
    ESHQ_SECRET

## Install

Add the following dependency to your project.clj file:

[clj-eshq "0.0.1"]

## Example

```clojure
(require 'eshq)

;; Get a token for a channel
(eshq/open {:channel "my-channel"})

;; Send a message to a channel
(eshq/send {:channel "my-channel" :data "{\"msg\": \"Hello, World!\"}"})
```
