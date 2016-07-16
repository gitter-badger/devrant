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

import com.google.inject.Injector;
import io.chapp.devrant.cli.tasks.Task;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class is responsible for running tasks by name. These tasks are configured in the /tasks.properties file.
 *
 * If the class is found the task will be instantiated using {@link com.google.inject.Guice} and executed.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public class TaskRunner {
    private static final Logger LOGGER = getLogger(TaskRunner.class);
    private static final Properties taskDefenitions = new Properties();

    static {
        try (InputStream stream = TaskRunner.class.getResourceAsStream("/tasks.properties")) {
            taskDefenitions.load(stream);
        } catch (IOException e) {
            LOGGER.error("Failed to load the task definitions!", e);
        }
    }

    private final Injector injector;

    @Inject
    public TaskRunner(Injector injector) {
        this.injector = injector;

    }

    public void run(String taskName) {
        String className = taskDefenitions.getProperty(taskName);

        if (className == null) {
            LOGGER.error("Task `{}` does not exist. Known tasks: {}", taskName, taskDefenitions.stringPropertyNames());
        } else {
            try {
                Class<?> taskClass = Class.forName(className);
                if (Task.class.isAssignableFrom(taskClass)) {
                    run((Class<? extends Task>) taskClass);
                } else {
                    LOGGER.error("The class {} is not a Task!", className);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.error("Could not find class {}", className, e);
            }
        }
    }

    private void run(Class<? extends Task> taskClass) {
        Task task = injector.getInstance(taskClass);
        task.execute();
    }
}
