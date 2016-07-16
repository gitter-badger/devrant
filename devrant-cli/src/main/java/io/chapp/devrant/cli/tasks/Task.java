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

/**
 * This interface represents a task that can be started from the command line.
 * The {@link io.chapp.devrant.cli.TaskRunner} will instantiate one of the classes that are
 * configured in /tasks.properties and execute it.
 *
 * @author Thomas Biesaart
 * @since 1.0.0
 */
public interface Task {
    void execute();
}
