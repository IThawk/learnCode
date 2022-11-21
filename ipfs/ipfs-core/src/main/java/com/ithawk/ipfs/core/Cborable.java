package com.ithawk.ipfs.core;

public interface Cborable {

    CborObject toCbor();

    default byte[] serialize() {
        return toCbor().toByteArray();
    }
}
