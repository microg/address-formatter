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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.microg.address.Formatter.getString;

public class Template {
    private String addressTemplate;
    private String useCountry;
    private String changeCountry;
    private List<Replacement> postformatReplace;
    private String addComponent;
    private String fallbackTemplate;
    private List<Replacement> replace;

    public String addressTemplate() {
        return addressTemplate;
    }

    public String useCountry() {
        return useCountry;
    }

    public String changeCountry() {
        return changeCountry;
    }

    public List<Replacement> postformatReplace() {
        return postformatReplace;
    }

    public String addComponent() {
        return addComponent;
    }

    public String fallbackTemplate() {
        return fallbackTemplate;
    }

    public List<Replacement> replace() {
        return replace;
    }

    private Template() {
    }

    public static Template parse(Object o) {
        Template res = new Template();
        if (o instanceof String) {
            res.addressTemplate = (String) o;
        } else if (o instanceof Map) {
            Map m = (Map) o;
            res.addressTemplate = getString(m, "address_template");
            res.useCountry = getString(m, "use_country");
            res.changeCountry = getString(m, "change_country");
            res.postformatReplace = getReplace(m, "postformat_replace");
            res.addComponent = getString(m, "add_component");
            res.fallbackTemplate = getString(m, "fallback_template");
            res.replace = getReplace(m, "replace");
        }
        return res;
    }

    private static List<Replacement> getReplace(Map m, String key) {
        if (!(m.get(key) instanceof List)) return Collections.emptyList();
        List l1 = (ArrayList) m.get(key);
        if (!l1.isEmpty()) {
            Object o2 = l1.get(0);
            if (o2 instanceof List) {
                List l2 = (List) o2;
                if (l2.size() == 2 && l2.get(0) instanceof String) {
                    List<Replacement> list = new ArrayList<Replacement>();
                    for (Object sl : l1) {
                        list.add(Replacement.create((List<String>) sl));
                    }
                    return list;
                }
            } else if (o2 instanceof String && l1.size() == 2) {
                return Collections.singletonList(Replacement.create(l1));
            }
        }
        return Collections.emptyList();
    }

    public static class Replacement {
        private String from;
        private String to;

        private Replacement(String from, String to) {
            this.from = from;
            this.to = to;
        }

        private static Replacement create(List<String> list) {
            return new Replacement(list.get(0), list.get(1));
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }
}
