/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.source.doctree;

import java.util.List;

/**
 * A tree node for an @serialData block tag.
 *
 * <p>
 * &#064;serialField field-name field-type field-description
 *
 * @since 1.8
 */
@jdk.Exported
public interface SerialFieldTree extends BlockTagTree {
    IdentifierTree getName();
    ReferenceTree getType();
    List<? extends DocTree> getDescription();
}
