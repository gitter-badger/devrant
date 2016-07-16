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

import com.google.inject.*;
import io.chapp.devrant.api.DevRant;
import org.apache.commons.cli.*;
import org.slf4j.Logger;

import javax.inject.Named;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class represents the main entry point for the application.
 * It is responsible for the configuration of the main components and execution of the application
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class DevRantCLI extends AbstractModule {
    public static final String OPTION_HELP = "h";
    public static final String OPTION_REFRESH = "r";
    private static final Logger LOGGER = getLogger(DevRantCLI.class);
    private final String[] arguments;
    private CommandLine commandLine;
    private Injector injector;

    DevRantCLI(String[] arguments) {
        this.arguments = arguments;
    }

    public static void main(String[] args) {
        LOGGER.debug("Starting DevRant Application");
        new DevRantCLI(args).execute();
    }

    private void execute() {
        injector = Guice.createInjector(this);
        UserInputParser userInputParser = injector.getInstance(UserInputParser.class);

        userInputParser.format().ifPresent(c -> {
            this.commandLine = c;
            runTasks();
        });
    }

    private void runTasks() {
        TaskRunner taskRunner = injector.getInstance(TaskRunner.class);
        List<String> tasks = commandLine.getArgList();
        tasks.forEach(taskRunner::run);
        if (tasks.isEmpty()) {
            taskRunner.run("show");
        }
    }

    @Override
    protected void configure() {
        bind(DevRantCLI.class).toInstance(this);
        bind(CommandLineParser.class).to(DefaultParser.class);
    }

    @Provides
    CommandLine getCommandLine() {
        return commandLine;
    }

    @Provides
    @Singleton
    DevRant devRant() {
        return DevRant.connect();
    }

    @Provides
    @Named("args")
    String[] getArguments() {
        return arguments;
    }

    @Provides
    @Singleton
    Options getOptions() {
        Options options = new Options();
        options.addOption(
                Option.builder(OPTION_HELP)
                        .longOpt("help")
                        .desc("Display this message")
                        .build()
        );

        options.addOption(
                Option.builder(OPTION_REFRESH)
                        .longOpt("refresh")
                        .desc("Drop the local cache so your next rant is new from the server")
                        .build()
        );

        return options;
    }
}
