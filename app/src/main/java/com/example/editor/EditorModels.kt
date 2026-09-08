package com.example.editor

import java.io.File

data class FileItem(
    val file: File,
    val isDirectory: Boolean,
    val name: String,
    val sizeBytes: Long,
    val lastModified: Long,
    val permissions: String,
    val isHidden: Boolean,
    val gitStatus: String? = null // "M", "U", "A", or null
)

data class EditorDocument(
    val file: File,
    val content: String,
    val isDirty: Boolean = false,
    val language: String = "text"
) {
    companion object {
        fun detectLanguage(fileName: String): String {
            return when {
                fileName.endsWith(".kt") || fileName.endsWith(".kts") -> "kotlin"
                fileName.endsWith(".sh") || fileName.endsWith(".bash") -> "shell"
                fileName.endsWith(".py") -> "python"
                fileName.endsWith(".json") -> "json"
                fileName.endsWith(".md") -> "markdown"
                fileName.endsWith(".xml") -> "xml"
                fileName.endsWith(".java") -> "java"
                fileName.endsWith(".c") || fileName.endsWith(".cpp") || fileName.endsWith(".h") -> "c"
                fileName.endsWith(".js") || fileName.endsWith(".ts") -> "javascript"
                fileName.endsWith(".yaml") || fileName.endsWith(".yml") -> "yaml"
                else -> "text"
            }
        }
    }
}
