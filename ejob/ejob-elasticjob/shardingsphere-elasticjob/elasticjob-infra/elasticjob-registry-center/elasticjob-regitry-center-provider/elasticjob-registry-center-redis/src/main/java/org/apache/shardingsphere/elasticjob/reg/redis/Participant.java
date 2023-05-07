//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

public class Participant {
    private final String id;
    private final boolean isLeader;

    public Participant(String id, boolean leader) {
        this.id = id;
        this.isLeader = leader;
    }

    Participant() {
        this("", false);
    }

    public String getId() {
        return this.id;
    }

    public boolean isLeader() {
        return this.isLeader;
    }

    public String toString() {
        return "Participant{id='" + this.id + '\'' + ", isLeader=" + this.isLeader + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Participant that = (Participant)o;
            if (this.isLeader != that.isLeader) {
                return false;
            } else {
                return this.id.equals(that.id);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + (this.isLeader ? 1 : 0);
        return result;
    }
}
