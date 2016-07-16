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
package io.chapp.devrant.cli.tasks;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.chapp.devrant.api.DevRant;
import io.chapp.devrant.api.model.Rant;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class ShowTask implements Task {
    private static final Logger LOGGER = getLogger(ShowTask.class);
    private static final String RANT_FORMAT = loadTemplate();
    private static final Path CACHE_FILE = Paths.get(System.getProperty("user.home"), ".devRantCLI", "rant_cache.json");
    private static final Path LAST_RANT_FILE = Paths.get(System.getProperty("user.home"), ".devRantCLI", "last_rant.json");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    private final DevRant devRant;

    @Inject
    public ShowTask(DevRant devRant) {
        this.devRant = devRant;
    }

    private static String loadTemplate() {
        try {
            return IOUtils.toString(ShowTask.class.getResource("/rant.template"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Failed to load the rant template!", e);
            return "";
        }
    }

    public static Optional<Rant> getLatestRant() {
        try {
            if (Files.exists(LAST_RANT_FILE)) {
                return Optional.of(
                        objectMapper.readValue(LAST_RANT_FILE.toFile(), Rant.class)
                );
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read last rant", e);
        }
        return Optional.empty();
    }

    public static void reset() {
        try {
            LOGGER.info("Dropping local cache");
            Files.deleteIfExists(CACHE_FILE);
            Files.deleteIfExists(LAST_RANT_FILE);
        } catch (IOException e) {
            LOGGER.error("Something went wrong while refreshing", e);
        }
    }

    @Override
    public void execute() {
        Rant rant = getFromCache();

        if (rant == null) {
            rant = cacheNewRants();
        }

        save(rant);
        LOGGER.info(String.format(
                RANT_FORMAT,
                rant.getUsername(),
                rant.getUserScore(),
                rant.getScore(),
                rant.getText()
        ));

        if(rant.getAttachedImage().getUrl() != null) {
            LOGGER.info("This Rant has an image. To view it run `rant open`.");
        }
    }

    private Rant cacheNewRants() {
        LOGGER.info("Fetching rants from devRant");
        List<Rant> rants = devRant.getRants();
        Rant rant = rants.remove(0);
        save(rants);
        return rant;
    }

    private Rant getFromCache() {
        if (Files.exists(CACHE_FILE)) {
            return rantFromFile();
        }

        LOGGER.debug("No rant was found in the cache");
        return null;
    }

    private Rant rantFromFile() {
        LOGGER.debug("Checking the cache for rants");
        try {
            List<Rant> rants = objectMapper.readValue(CACHE_FILE.toFile(), new TypeReference<List<Rant>>() {
            });
            if (rants.isEmpty()) {
                return null;
            }
            // Pop one item from the cache
            Rant rant = rants.get(0);
            rants.remove(0);
            save(rants);
            return rant;
        } catch (IOException e) {
            LOGGER.error("Failed to get rants from cache!", e);
        }

        // Cache might be corrupt so we from it
        try {
            Files.deleteIfExists(CACHE_FILE);
        } catch (IOException e) {
            LOGGER.debug("Dropping the cache failed as well!", e);
        }

        return null;
    }

    private void save(List<Rant> rants) {
        try {
            save(CACHE_FILE, rants);
        } catch (IOException e) {
            LOGGER.error("Failed to save rants to cache", e);
        }
    }

    private void save(Rant rant) {
        try {
            save(LAST_RANT_FILE, rant);
        } catch (IOException e) {
            LOGGER.error("Failed to save last rant", e);
        }
    }

    private void save(Path file, Object value) throws IOException {
        if (!Files.exists(file)) {
            Files.createDirectories(file.getParent());
            Files.createFile(file);
        }
        objectWriter.writeValue(file.toFile(), value);

    }
}
