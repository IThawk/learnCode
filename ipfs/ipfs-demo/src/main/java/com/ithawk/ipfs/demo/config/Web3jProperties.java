//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ithawk.ipfs.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "web3j"
)
public class Web3jProperties {
    public static final String WEB3J_PREFIX = "web3j";
    private String clientAddress;
    private Boolean adminClient;
    private String networkId;
    private Long httpTimeoutSeconds;

    public Web3jProperties() {
    }

    public String getClientAddress() {
        return this.clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Boolean isAdminClient() {
        return this.adminClient;
    }

    public void setAdminClient(Boolean adminClient) {
        this.adminClient = adminClient;
    }

    public String getNetworkId() {
        return this.networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public Long getHttpTimeoutSeconds() {
        return this.httpTimeoutSeconds;
    }

    public void setHttpTimeoutSeconds(Long httpTimeoutSeconds) {
        this.httpTimeoutSeconds = httpTimeoutSeconds;
    }
}
