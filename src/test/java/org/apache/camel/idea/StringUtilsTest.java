/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.idea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;

public class StringUtilsTest extends TestCase {

    @Test
    public void testHasQuestionMark() {
        assertTrue(StringUtils.hasQuestionMark("seda:foo?size=123"));
        assertFalse(StringUtils.hasQuestionMark("seda:foo"));
        assertFalse(StringUtils.hasQuestionMark(""));
        assertFalse(StringUtils.hasQuestionMark(null));
    }

    @Test
    public void testAsComponentName() {
        assertEquals("seda", StringUtils.asComponentName("seda:foo?size=123"));
        assertEquals("seda", StringUtils.asComponentName("seda:foo"));
        assertEquals(null, StringUtils.asComponentName("seda"));
    }

    @Test
    public void testAsLanguageName() {
        assertEquals("simple", StringUtils.asLanguageName("simple"));
        assertEquals("header", StringUtils.asLanguageName("header"));
        assertEquals("tokenize", StringUtils.asLanguageName("tokenize"));
        assertEquals("tokenize", StringUtils.asLanguageName("tokenizeXml"));
        assertEquals("javaScript", StringUtils.asLanguageName("js"));
        assertEquals("javaScript", StringUtils.asLanguageName("javascript"));
    }

    @Test
    public void testGetSafeValue() {
        Map<String, String> row = new HashMap<>();
        row.put("foo", "123");

        assertEquals("123", StringUtils.getSafeValue("foo", row));
        assertEquals("", StringUtils.getSafeValue("bar", row));

        Map<String, String> row2 = new HashMap<>();
        row2.put("bar", "true");

        List<Map<String, String>> rows = new ArrayList<>();
        rows.add(row);
        rows.add(row2);

        assertEquals("123", StringUtils.getSafeValue("foo", rows));
        assertEquals("true", StringUtils.getSafeValue("bar", rows));
        assertEquals("", StringUtils.getSafeValue("baz", rows));
    }

    @Test
    public void testWrapSeparator() {
        String url = "seda:foo?size=1234";

        assertEquals(url, StringUtils.wrapSeparator(url, "&", "\n", 80));

        String longUrl = "jms:queue:cheese?acknowledgementModeName=SESSION_TRANSACTED&asyncConsumer=true&cacheLevelName=CACHE_CONSUMER"
                + "&deliveryMode=2&errorHandlerLoggingLevel=DEBUG&explicitQosEnabled=true&jmsMessageType=Bytes";

        String wrapped = StringUtils.wrapSeparator(longUrl, "&", "\n", 120);

        String line1 = "jms:queue:cheese?acknowledgementModeName=SESSION_TRANSACTED&asyncConsumer=true&cacheLevelName=CACHE_CONSUMER&deliveryMode=2";
        String line2 = "&errorHandlerLoggingLevel=DEBUG&explicitQosEnabled=true&jmsMessageType=Bytes";

        String[] parts = wrapped.split("\n");
        assertEquals(2, parts.length);
        assertEquals(line1, parts[0]);
        assertEquals(line2, parts[1]);
    }

}
