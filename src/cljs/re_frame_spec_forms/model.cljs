(ns re-frame-spec-forms.model
  (:require [cljs.spec.alpha :as s]))

(s/def ::label (s/and string? #(<= 1 (count %) 64)))

(s/def ::preallocated boolean?)

(def supported-types #{"multihomed" "stub" "transit" "ixp"})
(s/def ::type supported-types)

(s/def ::tags (s/coll-of string?))

(s/def ::asn (s/and int? #(<= 1 % 65536)))
(s/def ::first ::asn)
(s/def ::last ::asn)
(s/def ::range (s/keys :req-un [::first ::last]))
(s/def ::ranges (s/coll-of ::range :distinct true :min-count 1))

(s/def ::create-payload
  (s/keys :req-un [::label ::preallocated ::ranges]
          :opt-un [::tags ::type]))



