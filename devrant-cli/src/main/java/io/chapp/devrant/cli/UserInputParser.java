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
package io.chapp.devrant.cli;


import io.chapp.devrant.cli.tasks.ShowTask;
import org.apache.commons.cli.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static io.chapp.devrant.cli.DevRantCLI.OPTION_HELP;
import static io.chapp.devrant.cli.DevRantCLI.OPTION_REFRESH;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class is responsible for parsing the initial user input.
 *
 * @author Thomas Biesaart
 */
public class UserInputParser {
    private static final Logger LOGGER = getLogger(UserInputParser.class);
    private final String[] args;
    private final Options options;
    private final CommandLineParser commandLineParser;

    @Inject
    public UserInputParser(@Named("args") String[] args, Options options, CommandLineParser commandLineParser) {
        this.args = args;
        this.options = options;
        this.commandLineParser = commandLineParser;
    }

    public Optional<CommandLine> format() {
        LOGGER.debug("Processing User Input");
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.hasOption(OPTION_HELP)) {
                LOGGER.info(printHelp());
                return Optional.empty();
            }
            if(commandLine.hasOption(OPTION_REFRESH)) {
                ShowTask.reset();
            }
            return Optional.of(commandLineParser.parse(options, args));
        } catch (ParseException e) {
            printHelp(e);
            return Optional.empty();
        }
    }

    private void printHelp(ParseException e) {
        LOGGER.error(e.getMessage() + "\n" + printHelp(), e);
    }

    private String printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); PrintWriter writer = new PrintWriter(outputStream)) {
            helpFormatter.printHelp(writer, 200, "rant", null, options, 8, 8, null, true);
            writer.flush();
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to report the error...", e);
        }
    }
}
