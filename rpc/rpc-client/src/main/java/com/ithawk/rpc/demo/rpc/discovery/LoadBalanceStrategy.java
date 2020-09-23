package com.ithawk.rpc.demo.rpc.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);

}
