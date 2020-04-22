package org.activiti.engine.test.api.history;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class NonCascadeDeleteTest extends PluggableActivitiTestCase {

  private static String PROCESS_DEFINITION_KEY = "oneTaskProcess";

  private String deploymentId;

  private String processInstanceId;

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
	  super.tearDown();
  }
  @Test
  public void testHistoricProcessInstanceQuery(){
    deploymentId = repositoryService.createDeployment()
      .addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess.bpmn20.xml")
      .deploy().getId();

    processInstanceId = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY).getId();
    Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    taskService.complete(task.getId());

    if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)) {
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        assertThat(processInstance.getProcessDefinitionKey()).isEqualTo(PROCESS_DEFINITION_KEY);

        // Delete deployment and historic process instance remains.
        repositoryService.deleteDeployment(deploymentId, false);

        HistoricProcessInstance processInstanceAfterDelete = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        assertThat(processInstanceAfterDelete.getProcessDefinitionKey()).isNull();
        assertThat(processInstanceAfterDelete.getProcessDefinitionName()).isNull();
        assertThat(processInstanceAfterDelete.getProcessDefinitionVersion()).isNull();

        // clean
        historyService.deleteHistoricProcessInstance(processInstanceId);
    }
  }
}
