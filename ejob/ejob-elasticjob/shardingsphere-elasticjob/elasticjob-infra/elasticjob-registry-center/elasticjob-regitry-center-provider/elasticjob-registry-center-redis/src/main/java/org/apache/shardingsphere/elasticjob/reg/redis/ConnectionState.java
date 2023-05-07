//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

public enum ConnectionState {
    CONNECTED {
        public boolean isConnected() {
            return true;
        }
    },
    SUSPENDED {
        public boolean isConnected() {
            return false;
        }
    },
    RECONNECTED {
        public boolean isConnected() {
            return true;
        }
    },
    LOST {
        public boolean isConnected() {
            return false;
        }
    },
    READ_ONLY {
        public boolean isConnected() {
            return true;
        }
    };

    private ConnectionState() {
    }

    public abstract boolean isConnected();
}
