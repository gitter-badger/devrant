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


import io.chapp.devrant.api.model.Comment;
import io.chapp.devrant.api.model.Rant;
import io.chapp.devrant.api.model.VoteState;

public class AuthenticatedDevRant extends DevRant {
    AuthenticatedDevRant(DevRantApi devRantApi) {
        super(devRantApi);
    }

    /**
     * Cast a vote on a rant.
     *
     * @param rantId    the id of the rant
     * @param voteState the vote to cast
     *
     * @return the updated rant
     */
    public Rant voteOnRant(int rantId, VoteState voteState) {
        return handleErrors(
                devRantApi.voteRant(rantId, voteState)
        ).getRant();
    }

    /**
     * Cast a vote on a comment.
     *
     * @param commentId the id of the comment
     * @param voteState the vote to cast
     *
     * @return the updated comment
     */
    public Comment voteOnComment(int commentId, VoteState voteState) {
        return handleErrors(
                devRantApi.voteComment(commentId, voteState)
        ).getComment();
    }
}
