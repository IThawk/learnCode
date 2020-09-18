package com.gupaoedu.rpc.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);

}
