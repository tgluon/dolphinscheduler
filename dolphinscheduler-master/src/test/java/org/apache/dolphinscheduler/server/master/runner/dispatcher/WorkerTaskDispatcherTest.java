/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.server.master.runner.dispatcher;

import org.apache.dolphinscheduler.extract.base.utils.Host;
import org.apache.dolphinscheduler.plugin.task.api.TaskExecutionContext;
import org.apache.dolphinscheduler.server.master.cluster.loadbalancer.IWorkerLoadBalancer;
import org.apache.dolphinscheduler.server.master.engine.task.runnable.ITaskExecutionRunnable;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WorkerTaskDispatcherTest {

    @Test
    public void getTaskInstanceDispatchHost() {
        IWorkerLoadBalancer workerLoadBalancer = Mockito.mock(IWorkerLoadBalancer.class);
        Mockito.when(workerLoadBalancer.select(Mockito.any())).thenReturn(Optional.of("localhost:1234"));
        WorkerTaskDispatcher workerTaskDispatcher = new WorkerTaskDispatcher(workerLoadBalancer);

        ITaskExecutionRunnable ITaskExecutionRunnable = Mockito.mock(ITaskExecutionRunnable.class);
        Mockito.when(ITaskExecutionRunnable.getTaskExecutionContext()).thenReturn(new TaskExecutionContext());
        Optional<Host> taskInstanceDispatchHost =
                workerTaskDispatcher.getTaskInstanceDispatchHost(ITaskExecutionRunnable);
        Assertions.assertEquals("localhost:1234", taskInstanceDispatchHost.get().getAddress());
    }
}
