# Retry Policy Configuration

Styx has a configurable retry policy. It attempts to re-send requests
under certain failure conditions, specifically when:

  - A TCP connection establishment for the chosen origin fails.
  - A connection has been pending (waiting at the connection pool) for
    the maximum allowed time (`pendingConnectionTimeoutMillis`).
  - A connection pool's pending connection queue is full. However this is
    unlikely unless Round Robin load balancing strategy is being used.

The number of times Styx will retry a request is determined by the value of
`count` in the configuration below. The default count is 1.

# Configuration

    retrypolicy:
      policy:
        factory:
          class: "com.hotels.styx.client.retry.RetryPolicyFactory"
          config: {count: 2}
      
      