//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

public class PathUtils {
    public PathUtils() {
    }

    public static void validatePath(String path, boolean isSequential) throws IllegalArgumentException {
        validatePath(isSequential ? path + "1" : path);
    }

    public static String validatePath(String path) throws IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        } else if (path.length() == 0) {
            throw new IllegalArgumentException("Path length must be > 0");
        } else if (path.charAt(0) != '/') {
            throw new IllegalArgumentException("Path must start with / character");
        } else if (path.length() == 1) {
            return path;
        } else if (path.charAt(path.length() - 1) == '/') {
            throw new IllegalArgumentException("Path must not end with / character");
        } else {
            String reason = null;
            char lastc = '/';
            char[] chars = path.toCharArray();

            for(int i = 1; i < chars.length; ++i) {
                char c = chars[i];
                if (c == 0) {
                    reason = "null character not allowed @" + i;
                    break;
                }

                if (c == '/' && lastc == '/') {
                    reason = "empty node name specified @" + i;
                    break;
                }

                if (c == '.' && lastc == '.') {
                    if (chars[i - 2] == '/' && (i + 1 == chars.length || chars[i + 1] == '/')) {
                        reason = "relative paths not allowed @" + i;
                        break;
                    }
                } else if (c == '.') {
                    if (chars[i - 1] == '/' && (i + 1 == chars.length || chars[i + 1] == '/')) {
                        reason = "relative paths not allowed @" + i;
                        break;
                    }
                } else if (c > 0 && c < 31 || c > 127 && c < 159 || c > '\ud800' && c < '\uf8ff' || c > '\ufff0' && c < '\uffff') {
                    reason = "invalid charater @" + i;
                    break;
                }

                lastc = chars[i];
            }

            if (reason != null) {
                throw new IllegalArgumentException("Invalid path string \"" + path + "\" caused by " + reason);
            } else {
                return path;
            }
        }
    }
}
