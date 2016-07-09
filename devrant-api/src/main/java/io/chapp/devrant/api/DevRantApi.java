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


import com.fasterxml.jackson.databind.ObjectMapper;
import io.chapp.devrant.api.model.AuthenticationToken;
import io.chapp.devrant.api.model.Sort;
import io.chapp.devrant.api.model.VoteState;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class DevRantApi {
    private static final String BASE_URL = "https://www.devrant.io/api/";
    private static final int APP_ID = 3;
    private final RequestFactory requestFactory;
    private final RequestExecutor requestExecutor;
    private final ObjectMapper objectMapper;
    private AuthenticationToken authenticationToken;

    DevRantApi(RequestFactory requestFactory, RequestExecutor requestExecutor, ObjectMapper objectMapper) {
        this.requestFactory = requestFactory;
        this.requestExecutor = requestExecutor;
        this.objectMapper = objectMapper;
    }

    public void authenticate(String username, String password) {
        Map<String, Object> parameters = parameters();
        parameters.put("username", username);
        parameters.put("password", password);

        AuthenticateResponse response = executePost(
                "users/auth-token",
                parameters,
                AuthenticateResponse.class
        );

        if (response.isSuccess()) {
            authenticationToken = response.getAuthenticationToken();
        } else {
            throw new DevRantException(response.getError());
        }
    }

    public VoteRantResponse voteRant(int rantId, VoteState vote) {
        Map<String, Object> parameters = authenticatedParameters();
        parameters.put("vote", vote.getInt());

        return executePost("devrant/rants/" + rantId + "/vote", parameters, VoteRantResponse.class);
    }

    public VoteCommentResponse voteComment(int commentId, VoteState vote) {
        Map<String, Object> parameters = authenticatedParameters();
        parameters.put("vote", vote.getInt());

        return executePost("comments/" + commentId + "/vote", parameters, VoteCommentResponse.class);
    }

    public GetRantResponse getRant(int id) {
        return executeGet("devrant/rants/" + id, parameters(), GetRantResponse.class);
    }

    public GetRantsResponse getRants(Sort sort, int limit, int skip) {
        Map<String, Object> parameters = parameters();
        parameters.put("sort", sort.name().toLowerCase());
        parameters.put("limit", limit);
        parameters.put("skip", skip);

        return executeGet("devrant/rants", parameters, GetRantsResponse.class);
    }

    public SearchResponse search(String query) {
        Map<String, Object> parameters = parameters();
        parameters.put("term", query);
        return executeGet("devrant/search", parameters, SearchResponse.class);
    }

    public GetUserIdResponse getUserId(String username) {
        Map<String, Object> parameters = parameters();
        parameters.put("username", username);
        return executeGet("get-user-id", parameters, GetUserIdResponse.class);
    }

    public GetUserProfileResponse getUserProfile(int id) {
        return executeGet("users/" + id, parameters(), GetUserProfileResponse.class);
    }

    private <T extends AbstractResponse> T executeGet(String path, Map<String, Object> parameters, Class<T> clazz) {
        return execute(path, requestFactory.buildGet(BASE_URL, path, parameters), clazz);
    }

    private <T extends AbstractResponse> T executePost(String path, Map<String, Object> parameters, Class<T> clazz) {
        return execute(path, requestFactory.buildPost(BASE_URL, path, parameters), clazz);
    }

    private <T extends AbstractResponse> T execute(String path, Request request, Class<T> clazz) {
        try (InputStream inputStream = requestExecutor.execute(request)) {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new DevRantExecutionException("Failed to get " + path, e);
        }
    }

    private Map<String, Object> authenticatedParameters() {
        if (authenticationToken == null) {
            throw new IllegalStateException("Please Authenticate Fist!");
        }
        return parameters();
    }

    private Map<String, Object> parameters() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("app", APP_ID);
        if (authenticationToken != null) {
            result.put("token_id", authenticationToken.getId());
            result.put("token_key", authenticationToken.getKey());
            result.put("user_id", authenticationToken.getUserId());
        }
        return result;
    }
}
