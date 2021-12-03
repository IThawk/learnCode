/**
 * Taotaosou.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package org.wltea.analyzer.dic;

/**
 * @author <a href="guotao@taotaosou.com">HuangGuotao</a>
 * @version 2013-8-29 下午5:13:22
 */
public class Term {
    String termText = "";
    String props = "";

    public String getTermText() {
        return termText;
    }

    public void setTermText(String termText) {
        this.termText = termText;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Term [termText=" + termText + ", props=" + props + "]";
    }

}
