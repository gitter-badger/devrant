/**
 * Copyright © 2016 Thomas Biesaart (thomas.biesaart@gmail.com)
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
package io.chapp.devrant.api;

import io.chapp.devrant.api.model.News;
import io.chapp.devrant.api.model.Rant;
import io.chapp.devrant.api.model.Setting;

import java.util.ArrayList;
import java.util.List;

public class GetRantsResponse extends AbstractResponse {
    private List<Rant> rants = new ArrayList<>();
    private List<Setting> settings = new ArrayList<>();
    private String set;
    private int wrw;
    private News news;


    public List<Rant> getRants() {
        return rants;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public String getSet() {
        return set;
    }

    public int getWrw() {
        return wrw;
    }

    public News getNews() {
        return news;
    }
}
