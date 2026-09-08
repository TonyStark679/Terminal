package com.example.editor

import java.io.File

class FileManager {

    fun listDirectory(dir: File, showHidden: Boolean = false): List<FileItem> {
        if (!dir.exists() || !dir.isDirectory) return emptyList()
        val files = dir.listFiles() ?: return emptyList()

        return files
            .filter { showHidden || !it.name.startsWith(".") }
            .sortedWith(compareBy({ !it.isDirectory }, { it.name.lowercase() }))
            .map { file ->
                val perms = buildString {
                    append(if (file.isDirectory) "d" else "-")
                    append(if (file.canRead()) "r" else "-")
                    append(if (file.canWrite()) "w" else "-")
                    append(if (file.canExecute()) "x" else "-")
                }
                FileItem(
                    file = file,
                    isDirectory = file.isDirectory,
                    name = file.name,
                    sizeBytes = if (file.isFile) file.length() else 0L,
                    lastModified = file.lastModified(),
                    permissions = perms,
                    isHidden = file.name.startsWith(".")
                )
            }
    }

    fun createFile(parentDir: File, fileName: String): File {
        val file = File(parentDir, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    fun createDirectory(parentDir: File, dirName: String): File {
        val dir = File(parentDir, dirName)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun deleteFileOrDirectory(file: File): Boolean {
        return if (file.isDirectory) {
            file.deleteRecursively()
        } else {
            file.delete()
        }
    }

    fun rename(file: File, newName: String): File {
        val target = File(file.parentFile, newName)
        file.renameTo(target)
        return target
    }

    fun readFile(file: File): String {
        return if (file.exists() && file.isFile) {
            file.readText()
        } else {
            ""
        }
    }

    fun writeFile(file: File, content: String) {
        file.writeText(content)
    }

    fun chmod(file: File, executable: Boolean): Boolean {
        return file.setExecutable(executable, false)
    }
}
