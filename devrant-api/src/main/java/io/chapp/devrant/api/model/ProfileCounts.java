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


public class ProfileCounts {
    private int rants;
    private int upvoted;
    private int comments;
    private int favorites;

    public int getRants() {
        return rants;
    }

    public int getUpvoted() {
        return upvoted;
    }

    public int getComments() {
        return comments;
    }

    public int getFavorites() {
        return favorites;
    }
}
