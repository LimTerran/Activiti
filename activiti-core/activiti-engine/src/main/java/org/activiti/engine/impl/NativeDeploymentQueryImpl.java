/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
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
package org.activiti.engine.impl;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.repository.NativeDeploymentQuery;
import org.activiti.engine.repository.Deployment;

import java.util.List;
import java.util.Map;

public class NativeDeploymentQueryImpl extends AbstractNativeQuery<NativeDeploymentQuery, Deployment> implements NativeDeploymentQuery {

  private static final long serialVersionUID = 1L;

  public NativeDeploymentQueryImpl(CommandContext commandContext) {
    super(commandContext);
  }

  public NativeDeploymentQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  // results ////////////////////////////////////////////////////////////////

  public List<Deployment> executeList(CommandContext commandContext, Map<String, Object> parameterMap, int firstResult, int maxResults) {
    return commandContext.getDeploymentEntityManager().findDeploymentsByNativeQuery(parameterMap, firstResult, maxResults);
  }

  public long executeCount(CommandContext commandContext, Map<String, Object> parameterMap) {
    return commandContext.getDeploymentEntityManager().findDeploymentCountByNativeQuery(parameterMap);
  }

}
