//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis.exception;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.yetus.audience.InterfaceAudience.Public;

@Public
public abstract class KeeperException extends Exception {
    private List<OpResult> results;
    private Code code;
    private String path;

    public static KeeperException create(Code code, String path) {
        KeeperException r = create(code);
        r.path = path;
        return r;
    }

    /** @deprecated */
    @Deprecated
    public static KeeperException create(int code, String path) {
        KeeperException r = create(KeeperException.Code.get(code));
        r.path = path;
        return r;
    }

    /** @deprecated */
    @Deprecated
    public static KeeperException create(int code) {
        return create(KeeperException.Code.get(code));
    }

    public static KeeperException create(Code code) {
        switch (code) {
            case SYSTEMERROR:
                return new SystemErrorException();
            case RUNTIMEINCONSISTENCY:
                return new RuntimeInconsistencyException();
            case DATAINCONSISTENCY:
                return new DataInconsistencyException();
            case CONNECTIONLOSS:
                return new ConnectionLossException();
            case MARSHALLINGERROR:
                return new MarshallingErrorException();
            case UNIMPLEMENTED:
                return new UnimplementedException();
            case OPERATIONTIMEOUT:
                return new OperationTimeoutException();
            case NEWCONFIGNOQUORUM:
                return new NewConfigNoQuorum();
            case RECONFIGINPROGRESS:
                return new ReconfigInProgress();
            case BADARGUMENTS:
                return new BadArgumentsException();
            case APIERROR:
                return new APIErrorException();
            case NONODE:
                return new NoNodeException();
            case NOAUTH:
                return new NoAuthException();
            case BADVERSION:
                return new BadVersionException();
            case NOCHILDRENFOREPHEMERALS:
                return new NoChildrenForEphemeralsException();
            case NODEEXISTS:
                return new NodeExistsException();
            case INVALIDACL:
                return new InvalidACLException();
            case AUTHFAILED:
                return new AuthFailedException();
            case NOTEMPTY:
                return new NotEmptyException();
            case SESSIONEXPIRED:
                return new SessionExpiredException();
            case INVALIDCALLBACK:
                return new InvalidCallbackException();
            case SESSIONMOVED:
                return new SessionMovedException();
            case NOTREADONLY:
                return new NotReadOnlyException();
            case EPHEMERALONLOCALSESSION:
                return new EphemeralOnLocalSessionException();
            case NOWATCHER:
                return new NoWatcherException();
            case RECONFIGDISABLED:
                return new ReconfigDisabledException();
            case SESSIONCLOSEDREQUIRESASLAUTH:
                return new SessionClosedRequireAuthException();
            case REQUESTTIMEOUT:
                return new RequestTimeoutException();
            case OK:
            default:
                throw new IllegalArgumentException("Invalid exception code");
        }
    }

    /** @deprecated */
    @Deprecated
    public void setCode(int code) {
        this.code = KeeperException.Code.get(code);
    }

    static String getCodeMessage(Code code) {
        switch (code) {
            case SYSTEMERROR:
                return "SystemError";
            case RUNTIMEINCONSISTENCY:
                return "RuntimeInconsistency";
            case DATAINCONSISTENCY:
                return "DataInconsistency";
            case CONNECTIONLOSS:
                return "ConnectionLoss";
            case MARSHALLINGERROR:
                return "MarshallingError";
            case UNIMPLEMENTED:
                return "Unimplemented";
            case OPERATIONTIMEOUT:
                return "OperationTimeout";
            case NEWCONFIGNOQUORUM:
                return "NewConfigNoQuorum";
            case RECONFIGINPROGRESS:
                return "ReconfigInProgress";
            case BADARGUMENTS:
                return "BadArguments";
            case APIERROR:
                return "APIError";
            case NONODE:
                return "NoNode";
            case NOAUTH:
                return "NoAuth";
            case BADVERSION:
                return "BadVersion";
            case NOCHILDRENFOREPHEMERALS:
                return "NoChildrenForEphemerals";
            case NODEEXISTS:
                return "NodeExists";
            case INVALIDACL:
                return "InvalidACL";
            case AUTHFAILED:
                return "AuthFailed";
            case NOTEMPTY:
                return "Directory not empty";
            case SESSIONEXPIRED:
                return "Session expired";
            case INVALIDCALLBACK:
                return "Invalid callback";
            case SESSIONMOVED:
                return "Session moved";
            case NOTREADONLY:
                return "Not a read-only call";
            case EPHEMERALONLOCALSESSION:
                return "Ephemeral node on local session";
            case NOWATCHER:
                return "No such watcher";
            case RECONFIGDISABLED:
                return "Reconfig is disabled";
            case SESSIONCLOSEDREQUIRESASLAUTH:
                return "Session closed because client failed to authenticate";
            case REQUESTTIMEOUT:
            default:
                return "Unknown error " + code;
            case OK:
                return "ok";
        }
    }

    public KeeperException(Code code) {
        this.code = code;
    }

    KeeperException(Code code, String path) {
        this.code = code;
        this.path = path;
    }

    /** @deprecated */
    @Deprecated
    public int getCode() {
        return this.code.code;
    }

    public Code code() {
        return this.code;
    }

    public String getPath() {
        return this.path;
    }

    public String getMessage() {
        return this.path != null && !this.path.isEmpty() ? "KeeperErrorCode = " + getCodeMessage(this.code) + " for " + this.path : "KeeperErrorCode = " + getCodeMessage(this.code);
    }

    void setMultiResults(List<OpResult> results) {
        this.results = results;
    }

    public List<OpResult> getResults() {
        return this.results != null ? new ArrayList(this.results) : null;
    }

    public static class RequestTimeoutException extends KeeperException {
        public RequestTimeoutException() {
            super(KeeperException.Code.REQUESTTIMEOUT);
        }
    }

    public static class SessionClosedRequireAuthException extends KeeperException {
        public SessionClosedRequireAuthException() {
            super(KeeperException.Code.SESSIONCLOSEDREQUIRESASLAUTH);
        }

        public SessionClosedRequireAuthException(String path) {
            super(KeeperException.Code.SESSIONCLOSEDREQUIRESASLAUTH, path);
        }
    }

    @Public
    public static class ReconfigDisabledException extends KeeperException {
        public ReconfigDisabledException() {
            super(KeeperException.Code.RECONFIGDISABLED);
        }

        public ReconfigDisabledException(String path) {
            super(KeeperException.Code.RECONFIGDISABLED, path);
        }
    }

    @Public
    public static class NoWatcherException extends KeeperException {
        public NoWatcherException() {
            super(KeeperException.Code.NOWATCHER);
        }

        public NoWatcherException(String path) {
            super(KeeperException.Code.NOWATCHER, path);
        }
    }

    @Public
    public static class UnimplementedException extends KeeperException {
        public UnimplementedException() {
            super(KeeperException.Code.UNIMPLEMENTED);
        }
    }

    @Public
    public static class SystemErrorException extends KeeperException {
        public SystemErrorException() {
            super(KeeperException.Code.SYSTEMERROR);
        }
    }

    @Public
    public static class EphemeralOnLocalSessionException extends KeeperException {
        public EphemeralOnLocalSessionException() {
            super(KeeperException.Code.EPHEMERALONLOCALSESSION);
        }
    }

    @Public
    public static class NotReadOnlyException extends KeeperException {
        public NotReadOnlyException() {
            super(KeeperException.Code.NOTREADONLY);
        }
    }

    @Public
    public static class SessionMovedException extends KeeperException {
        public SessionMovedException() {
            super(KeeperException.Code.SESSIONMOVED);
        }
    }

    @Public
    public static class UnknownSessionException extends KeeperException {
        public UnknownSessionException() {
            super(KeeperException.Code.UNKNOWNSESSION);
        }
    }

    @Public
    public static class SessionExpiredException extends KeeperException {
        public SessionExpiredException() {
            super(KeeperException.Code.SESSIONEXPIRED);
        }
    }

    @Public
    public static class RuntimeInconsistencyException extends KeeperException {
        public RuntimeInconsistencyException() {
            super(KeeperException.Code.RUNTIMEINCONSISTENCY);
        }
    }

    @Public
    public static class OperationTimeoutException extends KeeperException {
        public OperationTimeoutException() {
            super(KeeperException.Code.OPERATIONTIMEOUT);
        }
    }

    @Public
    public static class NotEmptyException extends KeeperException {
        public NotEmptyException() {
            super(KeeperException.Code.NOTEMPTY);
        }

        public NotEmptyException(String path) {
            super(KeeperException.Code.NOTEMPTY, path);
        }
    }

    @Public
    public static class NoNodeException extends KeeperException {
        public NoNodeException() {
            super(KeeperException.Code.NONODE);
        }

        public NoNodeException(String path) {
            super(KeeperException.Code.NONODE, path);
        }
    }

    @Public
    public static class NodeExistsException extends KeeperException {
        public NodeExistsException() {
            super(KeeperException.Code.NODEEXISTS);
        }

        public NodeExistsException(String path) {
            super(KeeperException.Code.NODEEXISTS, path);
        }
    }

    @Public
    public static class NoChildrenForEphemeralsException extends KeeperException {
        public NoChildrenForEphemeralsException() {
            super(KeeperException.Code.NOCHILDRENFOREPHEMERALS);
        }

        public NoChildrenForEphemeralsException(String path) {
            super(KeeperException.Code.NOCHILDRENFOREPHEMERALS, path);
        }
    }

    @Public
    public static class ReconfigInProgress extends KeeperException {
        public ReconfigInProgress() {
            super(KeeperException.Code.RECONFIGINPROGRESS);
        }
    }

    @Public
    public static class NewConfigNoQuorum extends KeeperException {
        public NewConfigNoQuorum() {
            super(KeeperException.Code.NEWCONFIGNOQUORUM);
        }
    }

    @Public
    public static class NoAuthException extends KeeperException {
        public NoAuthException() {
            super(KeeperException.Code.NOAUTH);
        }
    }

    @Public
    public static class MarshallingErrorException extends KeeperException {
        public MarshallingErrorException() {
            super(KeeperException.Code.MARSHALLINGERROR);
        }
    }

    @Public
    public static class InvalidCallbackException extends KeeperException {
        public InvalidCallbackException() {
            super(KeeperException.Code.INVALIDCALLBACK);
        }
    }

    @Public
    public static class InvalidACLException extends KeeperException {
        public InvalidACLException() {
            super(KeeperException.Code.INVALIDACL);
        }

        public InvalidACLException(String path) {
            super(KeeperException.Code.INVALIDACL, path);
        }
    }

    @Public
    public static class DataInconsistencyException extends KeeperException {
        public DataInconsistencyException() {
            super(KeeperException.Code.DATAINCONSISTENCY);
        }
    }

    @Public
    public static class ConnectionLossException extends KeeperException {
        public ConnectionLossException() {
            super(KeeperException.Code.CONNECTIONLOSS);
        }
    }

    @Public
    public static class BadVersionException extends KeeperException {
        public BadVersionException() {
            super(KeeperException.Code.BADVERSION);
        }

        public BadVersionException(String path) {
            super(KeeperException.Code.BADVERSION, path);
        }
    }

    @Public
    public static class BadArgumentsException extends KeeperException {
        public BadArgumentsException() {
            super(KeeperException.Code.BADARGUMENTS);
        }

        public BadArgumentsException(String path) {
            super(KeeperException.Code.BADARGUMENTS, path);
        }
    }

    @Public
    public static class AuthFailedException extends KeeperException {
        public AuthFailedException() {
            super(KeeperException.Code.AUTHFAILED);
        }
    }

    @Public
    public static class APIErrorException extends KeeperException {
        public APIErrorException() {
            super(KeeperException.Code.APIERROR);
        }
    }

    @Public
    public static enum Code implements CodeDeprecated {
        OK(0),
        SYSTEMERROR(-1),
        RUNTIMEINCONSISTENCY(-2),
        DATAINCONSISTENCY(-3),
        CONNECTIONLOSS(-4),
        MARSHALLINGERROR(-5),
        UNIMPLEMENTED(-6),
        OPERATIONTIMEOUT(-7),
        BADARGUMENTS(-8),
        NEWCONFIGNOQUORUM(-13),
        RECONFIGINPROGRESS(-14),
        UNKNOWNSESSION(-12),
        APIERROR(-100),
        NONODE(-101),
        NOAUTH(-102),
        BADVERSION(-103),
        NOCHILDRENFOREPHEMERALS(-108),
        NODEEXISTS(-110),
        NOTEMPTY(-111),
        SESSIONEXPIRED(-112),
        INVALIDCALLBACK(-113),
        INVALIDACL(-114),
        AUTHFAILED(-115),
        SESSIONMOVED(-118),
        NOTREADONLY(-119),
        EPHEMERALONLOCALSESSION(-120),
        NOWATCHER(-121),
        REQUESTTIMEOUT(-122),
        RECONFIGDISABLED(-123),
        SESSIONCLOSEDREQUIRESASLAUTH(-124);

        private static final Map<Integer, Code> lookup = new HashMap();
        private final int code;

        private Code(int code) {
            this.code = code;
        }

        public int intValue() {
            return this.code;
        }

        public static Code get(int code) {
            return (Code)lookup.get(code);
        }

        static {
            Iterator var0 = EnumSet.allOf(Code.class).iterator();

            while(var0.hasNext()) {
                Code c = (Code)var0.next();
                lookup.put(c.code, c);
            }

        }
    }

    /** @deprecated */
    @Deprecated
    @Public
    public interface CodeDeprecated {
        /** @deprecated */
        @Deprecated
        int Ok = 0;
        /** @deprecated */
        @Deprecated
        int SystemError = -1;
        /** @deprecated */
        @Deprecated
        int RuntimeInconsistency = -2;
        /** @deprecated */
        @Deprecated
        int DataInconsistency = -3;
        /** @deprecated */
        @Deprecated
        int ConnectionLoss = -4;
        /** @deprecated */
        @Deprecated
        int MarshallingError = -5;
        /** @deprecated */
        @Deprecated
        int Unimplemented = -6;
        /** @deprecated */
        @Deprecated
        int OperationTimeout = -7;
        /** @deprecated */
        @Deprecated
        int BadArguments = -8;
        /** @deprecated */
        @Deprecated
        int UnknownSession = -12;
        /** @deprecated */
        @Deprecated
        int NewConfigNoQuorum = -13;
        /** @deprecated */
        @Deprecated
        int ReconfigInProgress = -14;
        /** @deprecated */
        @Deprecated
        int APIError = -100;
        /** @deprecated */
        @Deprecated
        int NoNode = -101;
        /** @deprecated */
        @Deprecated
        int NoAuth = -102;
        /** @deprecated */
        @Deprecated
        int BadVersion = -103;
        /** @deprecated */
        @Deprecated
        int NoChildrenForEphemerals = -108;
        /** @deprecated */
        @Deprecated
        int NodeExists = -110;
        /** @deprecated */
        @Deprecated
        int NotEmpty = -111;
        /** @deprecated */
        @Deprecated
        int SessionExpired = -112;
        /** @deprecated */
        @Deprecated
        int InvalidCallback = -113;
        /** @deprecated */
        @Deprecated
        int InvalidACL = -114;
        /** @deprecated */
        @Deprecated
        int AuthFailed = -115;
        /** @deprecated */
        @Deprecated
        int EphemeralOnLocalSession = -120;
    }
}
