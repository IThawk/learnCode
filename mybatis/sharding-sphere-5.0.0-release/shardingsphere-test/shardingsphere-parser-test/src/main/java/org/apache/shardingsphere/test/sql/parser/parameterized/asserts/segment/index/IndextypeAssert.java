/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.index;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.sql.parser.sql.common.segment.ddl.index.IndextypeSegment;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.SQLCaseAssertContext;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.SQLSegmentAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.segment.owner.OwnerAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.asserts.value.IdentifierValueAssert;
import org.apache.shardingsphere.test.sql.parser.parameterized.jaxb.cases.domain.segment.impl.index.ExpectedIndextype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Indextype assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndextypeAssert {
    
    /**
     * Assert actual indextype segment is correct with expected indextype.
     *
     * @param assertContext assert context
     * @param actual actual indextype segment
     * @param expected expected indextype
     */
    public static void assertIs(final SQLCaseAssertContext assertContext, final IndextypeSegment actual, final ExpectedIndextype expected) {
        assertNotNull(assertContext.getText("Indextype should exist."), expected);
        IdentifierValueAssert.assertIs(assertContext, actual.getIdentifier(), expected, "Indextype");
        if (null != expected.getOwner()) {
            assertTrue(assertContext.getText("Actual owner should exist."), actual.getOwner().isPresent());
            OwnerAssert.assertIs(assertContext, actual.getOwner().get(), expected.getOwner());
        } else {
            assertFalse(assertContext.getText("Actual owner should not exist."), actual.getOwner().isPresent());
        }
        SQLSegmentAssert.assertIs(assertContext, actual, expected);
    }
}
