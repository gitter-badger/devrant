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
 * This class represents an image that is attached to a rand.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class Image {
    private String url;
    private int width;
    private int height;

    public Image() {

    }

    /**
     * This constructor is required for the {@link com.fasterxml.jackson.databind.ObjectMapper}
     * it does the exact same thing as the default constructor.
     *
     * @param workaround empty string
     */
    public Image(String workaround) {

    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
