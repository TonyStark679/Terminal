package com.example.automation

import com.example.data.database.AutomationWorkflow
import com.example.terminal.engine.TerminalSession
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import java.io.File

data class WorkflowExecutionLog(
    val workflowId: String,
    val workflowName: String,
    val triggerType: String,
    val status: String, // SUCCESS, FAILED, RUNNING
    val output: String,
    val timestamp: Long = System.currentTimeMillis()
)

class WorkflowEngine(
    private val onExecuteInTerminal: (command: String) -> Unit,
    private val onAiQuery: suspend (prompt: String) -> String
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _executionLogs = MutableStateFlow<List<WorkflowExecutionLog>>(emptyList())
    val executionLogs: StateFlow<List<WorkflowExecutionLog>> = _executionLogs.asStateFlow()

    fun onCommandExecuted(command: String, exitCode: Int, workingDir: String, workflows: List<AutomationWorkflow>) {
        scope.launch {
            for (wf in workflows) {
                if (!wf.isEnabled) continue
                when (wf.triggerType) {
                    "ON_ERROR" -> {
                        if (exitCode != 0) {
                            runWorkflow(wf, "Command failed with code $exitCode: $command", workingDir)
                        }
                    }
                    "ON_SUCCESS" -> {
                        if (exitCode == 0) {
                            runWorkflow(wf, "Command succeeded: $command", workingDir)
                        }
                    }
                    "ON_PATTERN" -> {
                        val pattern = parsePattern(wf.definitionJson)
                        if (pattern.isNotBlank() && command.contains(pattern)) {
                            runWorkflow(wf, "Command matched pattern '$pattern': $command", workingDir)
                        }
                    }
                }
            }
        }
    }

    private fun parsePattern(jsonStr: String): String {
        return try {
            JSONObject(jsonStr).optString("pattern", "")
        } catch (_: Exception) {
            ""
        }
    }

    private suspend fun runWorkflow(wf: AutomationWorkflow, contextInfo: String, workingDir: String) {
        val log = WorkflowExecutionLog(
            workflowId = wf.id,
            workflowName = wf.name,
            triggerType = wf.triggerType,
            status = "RUNNING",
            output = "Triggered by $contextInfo"
        )
        addLog(log)

        try {
            when (wf.actionType) {
                "NOTIFY" -> {
                    addLog(log.copy(status = "SUCCESS", output = "Notification sent: ${wf.name} - $contextInfo"))
                }
                "AI_EXPLAIN" -> {
                    val aiResp = onAiQuery("Explain error and suggest fix for: $contextInfo")
                    addLog(log.copy(status = "SUCCESS", output = "AI Explanation: ${aiResp.take(300)}"))
                }
                "EXECUTE_COMMAND" -> {
                    val cmdToRun = parseCommand(wf.definitionJson)
                    if (cmdToRun.isNotBlank()) {
                        onExecuteInTerminal(cmdToRun)
                        addLog(log.copy(status = "SUCCESS", output = "Dispatched command: $cmdToRun"))
                    } else {
                        addLog(log.copy(status = "SUCCESS", output = "Action triggered for: ${wf.name}"))
                    }
                }
                else -> {
                    addLog(log.copy(status = "SUCCESS", output = "Workflow completed successfully."))
                }
            }
        } catch (e: Exception) {
            addLog(log.copy(status = "FAILED", output = "Execution failed: ${e.message}"))
        }
    }

    private fun parseCommand(jsonStr: String): String {
        return try {
            JSONObject(jsonStr).optString("command", "")
        } catch (_: Exception) {
            ""
        }
    }

    private fun addLog(log: WorkflowExecutionLog) {
        val list = _executionLogs.value.toMutableList()
        list.add(0, log)
        if (list.size > 50) {
            _executionLogs.value = list.take(50)
        } else {
            _executionLogs.value = list
        }
    }
}
