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

import java.util.Date;

/**
 * This class represents an object which can be voted on.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public abstract class VoteComponent {
    @JsonProperty("num_upvotes")
    private int upvotes;
    @JsonProperty("num_downvotes")
    private int downvotes;
    @JsonProperty("created_time")
    private Date createdOn;
    private int score;
    @JsonProperty("num_comments")
    private int comments;
    @JsonProperty("vote_state")
    private VoteState voteState;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("user_username")
    private String username;
    @JsonProperty("user_score")
    private int userScore;

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public int getScore() {
        return score;
    }

    public int getComments() {
        return comments;
    }

    /**
     * Get the vote state of the current user.
     * If the request was not authenticated then no vote state will be present.
     *
     * @return the vote state
     */
    public VoteState getVoteState() {
        return voteState;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getUserScore() {
        return userScore;
    }
}
