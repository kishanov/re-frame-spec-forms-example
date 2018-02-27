(ns re-frame-spec-forms.backend-sim)


(def sample-asn-pool
  {:label        "My pool"
   :preallocated true
   :type         "transit"
   :tags         ["private", "qa-env"],
   :ranges       [{:first 100, :last 499}
                  {:first 1000, :last 1100}]})


(def asn-types
  [{:label "Multihoming" :value "multihomed"}
   {:label "Stub network" :value "stub"}
   {:label "Internet transit" :value "transit"}
   {:label "Internet exchange point" :value "ixp"}])
