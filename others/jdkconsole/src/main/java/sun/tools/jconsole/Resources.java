/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import sun.tools.jconsole.resources.JConsoleResources;

/**
 * Provides resource support for jconsole.
 */
public final class Resources {

    private static final Object lock = new Object();
    private static JConsoleResources resources = null;
    static {
	try {
	    resources =
		(JConsoleResources)ResourceBundle.getBundle("sun.tools.jconsole.resources.JConsoleResources");
	} catch (MissingResourceException e) {
	    // gracefully handle this later
	}
    }

    private Resources() { throw new AssertionError(); }

    /**
     * Returns the text of the jconsole resource for the specified key
     * formatted with the specified arguments.
     *
     */
    public static String getText(String key, Object... args) {
	String format = getString(key);
	if (format == null) {
	    format = "missing resource key: key = \"" + key + "\", " +
		"arguments = \"{0}\", \"{1}\", \"{2}\"";
	}
        return formatMessage(format, args);
    }

    static String formatMessage(String format, Object... args) {
        String ss = null;
        synchronized (lock) {
            /*
             * External synchronization required for safe use of
             * java.text.MessageFormat:
             */
            ss = MessageFormat.format(format, args);
        }
        return ss;
    }

    /**
     * Returns the mnemonic keycode int of the jconsole resource for the specified key.
     *
     */
    public static int getMnemonicInt(String key) {
	int mnemonic = 0;
	if (resources != null) {
	    Object obj = resources.getObject(key+".mnemonic");
	    if (obj instanceof Character) {
		mnemonic = (int)(Character)obj;
		if (mnemonic >= 'a' && mnemonic <='z') {
		    mnemonic -= ('a' - 'A');
		}
	    } else if (obj instanceof Integer) {
		mnemonic = (Integer)obj;
	    }
	}
	return mnemonic;
    }

    /**
     * Returns the jconsole resource string for the specified key.
     *
     */
    private static String getString(String key) {
	if (resources != null) {
	    try {
		return resources.getString(key);
	    } catch (MissingResourceException e) {
		return null;
	    }
	}
	return "missing resource bundle: key = \"" + key + "\", " +
	    "arguments = \"{0}\", \"{1}\", \"{2}\"";
    }
}
