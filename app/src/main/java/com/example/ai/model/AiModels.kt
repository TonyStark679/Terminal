package com.example.ai.model

enum class AiProviderType {
    OLLAMA,
    OPENAI_COMPATIBLE,
    GEMINI,
    LOCAL_ENGINE
}

enum class AiModelRole {
    AUTO_ROUTED,
    QWEN_CODING,
    GEMMA_TOT,
    FAST_EXPLAIN
}

data class AiModelConfig(
    val provider: AiProviderType = AiProviderType.OLLAMA,
    val ollamaEndpoint: String = "http://127.0.0.1:11434",
    val ollamaModel: String = "qwen2.5",
    val gemmaModel: String = "gemma3",
    val openAiEndpoint: String = "https://api.openai.com/v1",
    val openAiApiKey: String = "",
    val openAiModel: String = "gpt-4o-mini",
    val selectedRole: AiModelRole = AiModelRole.AUTO_ROUTED,
    val autoExecuteAllowed: Boolean = false,
    val contextLevel: AiContextLevel = AiContextLevel.TERMINAL
)

enum class AiContextLevel {
    NONE,
    COMMAND,
    TERMINAL,
    DIRECTORY,
    PROJECT,
    SELECTED_FILES
}

data class AiCommandProposal(
    val command: String,
    val explanation: String,
    val isDestructive: Boolean = false,
    val safetyWarning: String? = null
)

data class GemmaToTResult(
    val problem: String,
    val mode: String,
    val candidateApproaches: List<ApproachCandidate>,
    val selectedApproach: String,
    val finalRecommendation: String
)

data class ApproachCandidate(
    val title: String,
    val description: String,
    val advantages: List<String>,
    val disadvantages: List<String>
)

data class QwenReviewResult(
    val target: String,
    val summary: String,
    val findings: List<QwenFinding>,
    val proposedDiff: String? = null,
    val suggestedFix: String? = null
)

data class QwenFinding(
    val line: Int? = null,
    val severity: String, // INFO, WARNING, ERROR
    val message: String
)
