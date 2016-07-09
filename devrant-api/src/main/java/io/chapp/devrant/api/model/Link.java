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
package io.chapp.devrant.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a link which is attached to a rant.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class Link {
    private String type;
    private String url;
    @JsonProperty("short_url")
    private String shortUrl;
    private String title;
    private int start;
    private int end;
    private int special;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSpecial() {
        return special;
    }
}
