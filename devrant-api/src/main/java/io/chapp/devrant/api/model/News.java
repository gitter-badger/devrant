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

/**
 * This class represents a news message from the devRant feed.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class News {
    private int id;
    private String type;
    private String headline;
    private String body;
    private String footer;
    private int height;
    private String action;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBody() {
        return body;
    }

    public String getFooter() {
        return footer;
    }

    public int getHeight() {
        return height;
    }

    public String getAction() {
        return action;
    }
}
