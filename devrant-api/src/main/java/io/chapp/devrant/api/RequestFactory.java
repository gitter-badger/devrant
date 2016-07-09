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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

class RequestFactory {
    private final ObjectMapper objectMapper;

    RequestFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Request buildPost(String baseUrl, String path, Map<String, ?> parameters) {
        String url = baseUrl + path;
        try {
            return Request.Post(url).bodyString(
                    objectMapper.writeValueAsString(parameters),
                    ContentType.APPLICATION_JSON
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not parse json body");
        }
    }

    Request buildGet(String baseUrl, String path, Map<String, ?> parameters) {
        String url = parameters.entrySet()
                .stream()
                .map(this::parseParameter)
                .collect(Collectors.joining("&", baseUrl + path + "?", ""));

        return Request.Get(url);
    }

    private String parseParameter(Map.Entry<String, ?> entry) {
        return String.format(
                "%s=%s",
                entry.getKey(),
                encode(entry.getValue().toString())
        );
    }

    private String encode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("ENCODING NOT SUPPORTED", e);
        }
    }
}
