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
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.chapp.devrant.api.model.Rant;
import io.chapp.devrant.api.model.Sort;
import io.chapp.devrant.api.model.UserProfile;
import io.chapp.devrant.api.model.VoteState;
import org.apache.http.client.fluent.Executor;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.List;

/**
 * This class represents the main entry point of the {@link DevRant} api.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class DevRant {
    protected final DevRantApi devRantApi;

    DevRant(DevRantApi devRantApi) {
        this.devRantApi = devRantApi;
    }

    /**
     * Create an instance of {@link DevRant}.
     *
     * @return the api
     */
    public static DevRant connect() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(VoteState.class, new VoteStateDeserializer());
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(module);

        Executor executor = Executor.newInstance().use(new BasicCookieStore());

        return new DevRant(
                new DevRantApi(
                        new RequestFactory(objectMapper),
                        new RequestExecutor(
                                executor
                        ),
                        objectMapper
                )
        );
    }

    /**
     * Authenticate this instance of the api so you can make requests as a user.
     *
     * @param username the username
     * @param password the password
     *
     * @return the authenticated api
     */
    public AuthenticatedDevRant authenticate(String username, String password) {
        devRantApi.authenticate(username, password);
        return new AuthenticatedDevRant(devRantApi);
    }

    /**
     * Get a single rant
     *
     * @param id the rant id
     *
     * @return the rant
     */
    public GetRantResponse getRant(int id) {
        return handleErrors(
                devRantApi.getRant(id)
        );
    }

    /**
     * List the most recent rants.
     *
     * @return the rants
     */
    public List<Rant> getRants() {
        return getRants(0);
    }

    /**
     * List the most recent rants but skip some.
     *
     * @param skip the number of rants to skip
     *
     * @return the rants
     */
    public List<Rant> getRants(int skip) {
        return handleErrors(
                devRantApi.getRants(
                        Sort.RECENT,
                        50,
                        skip
                )
        ).getRants();
    }

    /**
     * Search for rants using a search query
     *
     * @param query the query
     *
     * @return the rants
     */
    public List<Rant> search(String query) {
        return handleErrors(
                devRantApi.search(query)
        ).getResults();
    }

    /**
     * Fetch information about a user.
     *
     * @param userId the user id
     *
     * @return the profile
     */
    public UserProfile getUserProfile(int userId) {
        return handleErrors(
                devRantApi.getUserProfile(userId)
        ).getProfile();
    }

    /**
     * Get the id of a user with a given username.
     *
     * @param username the username
     *
     * @return the id
     */
    public int getUserId(String username) {
        return handleErrors(
                devRantApi.getUserId(username)
        ).getUserId();
    }

    protected <T extends AbstractResponse> T handleErrors(T response) {
        if (!response.isSuccess()) {
            throw new DevRantException(response.getError());
        }
        return response;
    }

}
