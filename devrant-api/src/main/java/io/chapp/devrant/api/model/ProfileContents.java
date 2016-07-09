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

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an insight into the contents of a
 * {@link UserProfile}.
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class ProfileContents {
    private List<Rant> rants = new ArrayList<>();
    private List<Rant> upvoted = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Rant> favorites = new ArrayList<>();

    public List<Rant> getRants() {
        return rants;
    }

    public List<Rant> getUpvoted() {
        return upvoted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Rant> getFavorites() {
        return favorites;
    }
}
