/*
 * Copyright 2013-2016 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.address;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@RunWith(Parameterized.class)
public class IntegrationTestSuite {

    private String testFile;
    private String tag;

    @Parameterized.Parameters
    public static Collection<String> test() throws IOException {
        return Formatter.findFilesInPath("org/microg/address/testcases/countries", "*.yaml");
    }

    public IntegrationTestSuite(String testFile) {
        this.testFile = testFile;
        this.tag = testFile.substring(testFile.lastIndexOf("/") + 1, testFile.length() - 5);
    }

    @Test
    public void integrationTestCase() throws IOException {
        Formatter formatter = new Formatter();
        for (Object o : Formatter.loadFile(testFile)) {
            Map testCase = (Map) o;
            Map<String, String> components = (Map<String, String>) testCase.get("components");
            String description = (String) testCase.get("description");
            String expected = ((String) testCase.get("expected")).trim();
            String actual = formatter.formatAddress(components);
            Assert.assertEquals("[" + tag + "] " + description, expected.replace("\n", "|"), actual.replace("\n", "|"));
            Assert.assertEquals(description, expected, actual);
        }
    }
}
