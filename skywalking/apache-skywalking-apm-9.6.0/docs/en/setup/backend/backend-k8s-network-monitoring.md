# Kubernetes Network monitoring
SkyWalking leverages [SkyWalking Rover](https://github.com/apache/skywalking-rover) [network profiling feature](https://skywalking.apache.org/docs/skywalking-rover/next/en/setup/configuration/profiling/#network)
to measure network performance for particular pods on-demand, including metrics of L4(TCP) and L7(HTTP) traffic
and raw data of HTTP requests and responses.
Underlying, SkyWalking Rover converts data from socket data to metrics using eBPF technology.

## Data flow
1. SkyWalking OAP server observes which specific k8s pod needs to monitor the network.
2. SkyWalking Rover receives tasks from SkyWalking OAP server and executes them, and converts the network data into metrics send to the backend service.
3. The SkyWalking OAP Server accesses K8s's `API Server` to fetch meta info and parses the expression with [MAL](../../concepts-and-designs/mal.md) to aggregate.

## Setup
1. Setup [SkyWalking Rover](https://skywalking.apache.org/docs/skywalking-rover/next/en/setup/overview/).
2. Enable the network profiling MAL file in the OAP server.
```yaml
agent-analyzer:
  selector: ${SW_AGENT_ANALYZER:default}
  default:
    meterAnalyzerActiveFiles: ${SW_METER_ANALYZER_ACTIVE_FILES:network-profiling}
```

## Sampling config

**Notice the precondition, the HTTP request must have the trace header in SkyWalking(`sw8` header) or Zipkin(`b3` header(s)) format.**

The sampling configurations define the sampling boundaries for the HTTP traffic. When a HTTP calling is sampled,
the SkyWalking Rover could collect the HTTP request/response raw data and upload it to the span attached event. 

The sampling config contains multiple rules, and each of rules has the following configurations:
1. `URI Regex`: The match pattern for HTTP requests is HTTP URI-oriented. Match all requests if the URI regex is not set.
2. `Minimal Request Duration (ms)`: Sample the HTTP requests with slower latency than this threshold.
3. `Sample HTTP requests and responses with tracing when the response code is between 400 and 499`: This is OFF by default.
4. `Sample HTTP requests and responses with tracing when the response code is between 500 and 599`: This is ON by default.

## Supported metrics

After SkyWalking OAP server receives the metrics from the SkyWalking Rover, it supports to analysis the following data:
1. **Topology**: Based on the process and peer address, the following topology data is supported:
   1. **Relation**: Analyze the relationship between local processes, or local process with external pods or services.
   2. **SSL**: The socket read or write package with SSL.
   3. **Protocol**: The protocols for write or read data.
2. TCP socket read and write metrics, including following types:
   1. **Call Per Minute**: The count of the socket read or write.
   2. **Bytes**: The package size of the socket data.
   3. **Execute Time**: The executed time of the socket read or write.
   4. **Connect**: The socket connect/accept with peer address count and execute time.
   5. **Close**: The socket close the socket count and execute time.
   6. **RTT**: The RTT(Round Trip Time) of socket communicate with peer address.
3. Local process communicate with peer address exception data, including following types:
   1. **Retransmit**: The count of TCP package is retransmitted.
   2. **Drop**: The count of TCP package is dropped.
4. HTTP/1.x request/response related metrics, including following types:
   1. **Request CPM**: The calls per minute of requests.
   2. **Response CPM**: The calls per minute of responses with status code.
   3. **Request Package Size**: The size(KB) of the request package.
   4. **Response Package Size**: The size(KB) of the response package.
   5. **Client Side Response Duration**: The duration(ms) of the client receive the response.
   6. **Server Side Response Duration**: The duration(ms) of the server send the response.
5. HTTP sampled request with traces, including following types:
   1. **Slow traces**: The traces which have slow duration.
   2. **Traces from HTTP Code in [400, 500) (ms)**: The traces which response status code in [400, 500).
   3. **Traces from HTTP Code in [500, 600) (ms)**: The traces which response status code in [500, 600).
