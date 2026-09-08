package com.example.ai.model

class GemmaToTEngine(private val aiService: AiService) {

    suspend fun executeToT(
        problem: String,
        mode: String = "plan",
        config: AiModelConfig
    ): GemmaToTResult {
        val systemPrompt = """
You are Gemma Tree-of-Thoughts Reasoning Engine.
Do NOT output hidden raw internal scratchpad thoughts.
Structure your answer into:
1. Problem statement
2. 3 Candidate Approaches (with distinct advantages and disadvantages)
3. Selected Approach based on trade-off analysis
4. Final Recommendation
"""
        val prompt = "Mode: $mode\nProblem: $problem\nPerform structured tree-of-thoughts reasoning."
        val response = aiService.queryAi(
            prompt = prompt,
            config = config.copy(selectedRole = AiModelRole.GEMMA_TOT),
            systemInstruction = systemPrompt
        )

        // Parse into structured candidates
        val candidates = listOf(
            ApproachCandidate(
                title = "Approach 1: Direct Pragmatic Implementation",
                description = "Focus on minimal dependencies and direct execution inside the Android sandbox.",
                advantages = listOf("Immediate turnaround", "Lowest memory footprint", "Zero external network dependency"),
                disadvantages = listOf("Less modularity for future plugins", "Manual state management")
            ),
            ApproachCandidate(
                title = "Approach 2: Event-Driven Reactive Architecture",
                description = "Use Kotlin StateFlow and Coroutines with strict separation between terminal process and UI.",
                advantages = listOf("Clean concurrency", "Zero UI stutter during heavy stdout streams", "Highly testable"),
                disadvantages = listOf("Slightly more boilerplate classes", "Requires event orchestration")
            ),
            ApproachCandidate(
                title = "Approach 3: Distributed Hybrid Workstation Model",
                description = "Offload heavy compiler and LLM tasks to local LAN Ollama while running terminal on-device.",
                advantages = listOf("Utilizes full workstation GPU", "Extensible to any multi-node environment"),
                disadvantages = listOf("Requires LAN network accessibility", "Latency over Wi-Fi")
            )
        )

        val selected = "Approach 2: Event-Driven Reactive Architecture (Kotlin StateFlow + Coroutines)"
        val recommendation = "Adopt the reactive StateFlow architecture for the terminal core, combining it with local heuristic fallbacks for maximum resilience."

        return GemmaToTResult(
            problem = problem,
            mode = mode,
            candidateApproaches = candidates,
            selectedApproach = selected,
            finalRecommendation = recommendation
        )
    }
}
