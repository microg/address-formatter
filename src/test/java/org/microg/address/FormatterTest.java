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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormatterTest {

    private static Map<String, String> getTourEiffel3eEtageComponents() {
        Map<String, String> components = new HashMap<String, String>();
        components.put("viewpoint", "Tour Eiffel 3e étage");
        components.put("road", "Avenue Gustave Eiffel");
        components.put("suburb", "Gros-Caillou");
        components.put("city_district", "7th Arrondissement");
        components.put("city", "Paris");
        components.put("county", "Paris");
        components.put("state", "Ile-de-France");
        components.put("country", "France");
        components.put("postcode", "75007");
        components.put("country_code", "fr");
        return components;
    }

    @Test
    public void testGuessNameSpecialBuilding() throws IOException {
        Assert.assertEquals("Tour Eiffel 3e étage", new Formatter().guessName(getTourEiffel3eEtageComponents()));
    }

    @Test
    public void testGuessTypeCandidatesSpecialBuilding() throws IOException {
        Assert.assertTrue(new Formatter().guessTypeCandidates(getTourEiffel3eEtageComponents()).contains("viewpoint"));
    }
}
