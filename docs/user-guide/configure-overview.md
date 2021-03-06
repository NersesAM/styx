## Configuring Styx environment

Styx supports a YAML-based configuration. A yaml file can be specified using a system property `YAML_CONFIG_LOCATION=your/file/path`.
By default a file is taken from the classpath at `classpath:conf/environment/styx-config.yaml`.
Regardless of file location, these properties can all be overwritten using system properties by the same name.


### Example styx-config

    ---

    # A string uniquely identifying the host running the application, must be different for all running instances of the application
    # the default value is suitable only for non clustered environments
    jvmRouteName: "${jvm.route:noJvmRouteSet}"


    proxy:
      connectors:
      - type: http
        # Server port for Styx proxy.
        port: 8080

      #  0 -> availableProcessors / 2 threads will be used
      bossThreadsCount: 1

      # styx client worker threads are those performing all the asynchronous I/O operation to the backend origins.
      # 0 -> availableProcessors / 2 threads will be used
      clientWorkerThreadsCount: 0

      # Worker threads are those performing all the asynchronous I/O operation on the inbound channel.
      # 0 -> availableProcessors / 2 threads will be used

      workerThreadsCount: 0

      tcpNoDelay: true
      nioReuseAddress: true
      nioKeepAlive: true
      maxInitialLength: 4096
      maxHeaderSize: 8192
      maxChunkSize: 8192
      maxContentLength: 65536

      # Time in milliseconds Styx Proxy Service waits for an incoming request from client
      # before replying with 408 Request Timeout.
      requestTimeoutMillis: 12000


    admin:
      connectors:
      - type: http
        # Server port for Styx admin interface.
        port: 9000

      # Number of threads for handling incoming connections on admin interface. 0 -> availableProcessors / 2 threads will be used.
      bossThreadsCount: 1

      # Number of worker threads for admin interface
      # Worker threads are those performing all the asynchronous I/O operation on the inbound channel.
      # 0 -> availableProcessors / 2 threads will be used
      workerThreadsCount: 1

      tcpNoDelay: true
      nioReuseAddress: true
      nioKeepAlive: true
      maxInitialLength: 4096
      maxHeaderSize: 8192
      maxChunkSize: 8192
      maxContentLength: 65536
      
      # Whether to cache the generated JSON for the /admin/metrics and /admin/jvm pages
      metricsCache:
        enabled: true
        expirationMillis: 10000

    loadBalancing:
      # Load balancer strategy type. Allowed values: "LEAST\_RESPONSE\_TIME", "ROUND\_ROBIN", and "ADAPTIVE". Defaults to "ROUND_ROBIN".
      strategy: "ADAPTIVE"


      adaptive:
        # Adaptive loadbalancer warm-up count. The count is the number of requests that has to hit the
        # load balancer before it upswitches from round robin strategy to least response time strategy.
        warmupCount: 100

    metrics:
      graphite:
        enabled: true
        # Host of the Graphite endpoint. Overrides the property from CoreFoundation
        host: "destination.host"

        # Port of the Graphite endpoint. Overrides the property from CoreFoundation
        port: 2003

        # Graphite reporting interval in milliseconds
        intervalMillis: 5000
      jmx:
        # Enable reporting of metrics via JMX. Overrides the property from CoreFoundation
        enabled: true
        
    request-logging:
      # Enabled logging of requests and responses (with requestId to match them up)
      # Logs are produced on server and client side, so there is an information on 
      # how the server-side (inbound) and client-side (outbound) request/response look like.
      # In long format log entry contains additionally headers and cookies. 
      inbound:
        enabled: true
        longFormat: true
      outbound:
        enabled: true
        longFormat: true
      
    # Determines the format of the response info header
    responseInfoHeaderFormat: {INSTANCE};{REQUEST_ID}
    
    # Configures the names of the headers that Styx adds to messages it proxies (see headers.md)
    # If not configured, defaults will be used.
    styxHeaders:
      styxInfo:
        name: "X-Styx-Info"
        format: "{INSTANCE};{REQUEST_ID}"
      originId:
        name: "X-Styx-Origin-Id"
      requestId:
        name: "X-Styx-Request-Id"


Without the comments, it looks like this:

    ---

    jvmRouteName: "${jvm.route:noJvmRouteSet}"

    proxy:
      connectors:
      - type: http
        port: 8080
      bossThreadsCount: 1
      clientWorkerThreadsCount: 0
      workerThreadsCount: 0
      tcpNoDelay: true
      nioReuseAddress: true
      nioKeepAlive: true
      maxInitialLength: 4096
      maxHeaderSize: 8192
      maxChunkSize: 8192
      maxContentLength: 65536
      requestTimeoutMillis: 12000

    admin:
      connectors:
      - type: http
        port: 9000
      bossThreadsCount: 1
      workerThreadsCount: 1
      tcpNoDelay: true
      nioReuseAddress: true
      nioKeepAlive: true
      maxInitialLength: 4096
      maxHeaderSize: 8192
      maxChunkSize: 8192
      maxContentLength: 65536
      metricsCache:
        enabled: true
        expirationMillis: 10000

    loadBalancing:
      strategy: "ADAPTIVE"
      adaptive:
        warmupCount: 100

    metrics:
      graphite:
        enabled: true
        host: "destination.host"
        port: 2003
        intervalMillis: 5000
      jmx:
        enabled: true

    request-logging:
      enabled: true

    styxHeaders:
      styxInfo:
        name: "X-Styx-Info"
        format: "{INSTANCE};{REQUEST_ID}"
      originId:
        name: "X-Styx-Origin-Id"
      requestId:
        name: "X-Styx-Request-Id"

