//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ithawk.ipfs.demo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.util.Assert;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetListening;

public class Web3jHealthIndicator extends AbstractHealthIndicator {
    private Web3j web3j;

    public Web3jHealthIndicator(Web3j web3j) {
        Assert.notNull(web3j, "Web3j must not be null");
        this.web3j = web3j;
    }

    protected void doHealthCheck(Builder builder) throws Exception {
        try {
            boolean listening = ((NetListening)this.web3j.netListening().send()).isListening();
            if (!listening) {
                builder.down();
            } else {
                builder.up();
                List<CompletableFuture> futures = new ArrayList();
                futures.add(this.web3j.netVersion().sendAsync().thenApply((netVersion) -> {
                    return builder.withDetail("netVersion", netVersion.getNetVersion());
                }));
                futures.add(this.web3j.web3ClientVersion().sendAsync().thenApply((web3ClientVersion) -> {
                    return builder.withDetail("clientVersion", web3ClientVersion.getWeb3ClientVersion());
                }));
                futures.add(this.web3j.ethBlockNumber().sendAsync().thenApply((ethBlockNumber) -> {
                    return builder.withDetail("blockNumber", ethBlockNumber.getBlockNumber());
                }));
                futures.add(this.web3j.ethProtocolVersion().sendAsync().thenApply((ethProtocolVersion) -> {
                    return builder.withDetail("protocolVersion", ethProtocolVersion.getProtocolVersion());
                }));
                futures.add(this.web3j.netPeerCount().sendAsync().thenApply((netPeerCount) -> {
                    return builder.withDetail("netPeerCount", netPeerCount.getQuantity());
                }));
                CompletableFuture.allOf((CompletableFuture[])futures.toArray(new CompletableFuture[futures.size()])).get();
            }
        } catch (Exception var4) {
            builder.down(var4);
        }

    }
}
