//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis.exception;

import java.util.Arrays;
import java.util.List;
import org.apache.zookeeper.data.Stat;

public abstract class OpResult {
    private int type;

    private OpResult(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public static class ErrorResult extends OpResult {
        private int err;

        public ErrorResult(int err) {
            super(-1);
//            super(-1, null);
            this.err = err;
        }

        public int getErr() {
            return this.err;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof ErrorResult)) {
                return false;
            } else {
                ErrorResult other = (ErrorResult)o;
                return this.getType() == other.getType() && this.err == other.getErr();
            }
        }

        public int hashCode() {
            return this.getType() * 35 + this.err;
        }
    }

    public static class GetDataResult extends OpResult {
        private byte[] data;
        private Stat stat;

        public GetDataResult(byte[] data, Stat stat) {
            super(4);
            this.data = data == null ? null : Arrays.copyOf(data, data.length);
            this.stat = stat;
        }

        public byte[] getData() {
            return this.data == null ? null : Arrays.copyOf(this.data, this.data.length);
        }

        public Stat getStat() {
            return this.stat;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof GetDataResult)) {
                return false;
            } else {
                GetDataResult other = (GetDataResult)o;
                return this.getType() == other.getType() && this.stat.equals(other.stat) && Arrays.equals(this.data, other.data);
            }
        }

        public int hashCode() {
            return (int)((long)(this.getType() * 35) + this.stat.getMzxid() + (long)Arrays.hashCode(this.data));
        }
    }

    public static class GetChildrenResult extends OpResult {
        private List<String> children;

        public GetChildrenResult(List<String> children) {
            super(8);
            this.children = children;
        }

        public List<String> getChildren() {
            return this.children;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof GetChildrenResult)) {
                return false;
            } else {
                GetChildrenResult other = (GetChildrenResult)o;
                return this.getType() == other.getType() && this.children.equals(other.children);
            }
        }

        public int hashCode() {
            return this.getType() * 35 + this.children.hashCode();
        }
    }

    public static class CheckResult extends OpResult {
        public CheckResult() {
            super(13);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof CheckResult)) {
                return false;
            } else {
                CheckResult other = (CheckResult)o;
                return this.getType() == other.getType();
            }
        }

        public int hashCode() {
            return this.getType();
        }
    }

    public static class SetDataResult extends OpResult {
        private Stat stat;

        public SetDataResult(Stat stat) {
            super(5);
            this.stat = stat;
        }

        public Stat getStat() {
            return this.stat;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof SetDataResult)) {
                return false;
            } else {
                SetDataResult other = (SetDataResult)o;
                return this.getType() == other.getType() && this.stat.getMzxid() == other.stat.getMzxid();
            }
        }

        public int hashCode() {
            return (int)((long)(this.getType() * 35) + this.stat.getMzxid());
        }
    }

    public static class DeleteResult extends OpResult {
        public DeleteResult() {
            super(2);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof DeleteResult)) {
                return false;
            } else {
                DeleteResult opResult = (DeleteResult)o;
                return this.getType() == opResult.getType();
            }
        }

        public int hashCode() {
            return this.getType();
        }
    }

    public static class CreateResult extends OpResult {
        private String path;
        private Stat stat;

        public CreateResult(String path) {
            this(1, path, (Stat)null);
        }

        public CreateResult(String path, Stat stat) {
            this(15, path, stat);
        }

        private CreateResult(int opcode, String path, Stat stat) {
            super(opcode);
            this.path = path;
            this.stat = stat;
        }

        public String getPath() {
            return this.path;
        }

        public Stat getStat() {
            return this.stat;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof CreateResult)) {
                return false;
            } else {
                CreateResult other = (CreateResult)o;
                boolean statsAreEqual = this.stat == null && other.stat == null || this.stat != null && other.stat != null && this.stat.getMzxid() == other.stat.getMzxid();
                return this.getType() == other.getType() && this.path.equals(other.getPath()) && statsAreEqual;
            }
        }

        public int hashCode() {
            return (int)((long)(this.getType() * 35 + this.path.hashCode()) + (this.stat == null ? 0L : this.stat.getMzxid()));
        }
    }
}
