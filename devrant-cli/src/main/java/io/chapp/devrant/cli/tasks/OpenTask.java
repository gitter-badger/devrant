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


import io.chapp.devrant.api.model.Rant;
import org.slf4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This task will open the latest rant if awt {@link Desktop} is supported by the
 * current platform.
 *
 * @author Thomas Biesaart
 */
public class OpenTask implements Task {
    private static final Logger LOGGER = getLogger(OpenTask.class);

    @Override
    public void execute() {
        Rant rant = ShowTask.getLatestRant().orElse(null);

        if (rant != null) {
            open(rant);
        } else {
            LOGGER.error("Use the `rant` command first to read rants.");
        }
    }

    private void open(Rant rant) {
        String url = "https://www.devrant.io/rants/" + rant.getId();
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException | UnsupportedOperationException e) {
            LOGGER.error("Sorry, I cannot open rants on this platform.\nGo To: " + url, e);
        }
    }
}
