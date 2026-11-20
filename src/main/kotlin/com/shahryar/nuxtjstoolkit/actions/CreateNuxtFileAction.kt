
package com.shahryar.nuxtjstoolkit.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.shahryar.nuxtjstoolkit.generators.NuxtTemplateDefinition

class CreateNuxtFileAction(
    private val template: NuxtTemplateDefinition
) : AnAction(
    template.title,
    null,
    template.icon
), DumbAware {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(event: AnActionEvent) {
        val directory = getTargetDirectory(event)

        event.presentation.isEnabledAndVisible =
            event.project != null &&
                    directory != null &&
                    directory.isWritable
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val directory = getTargetDirectory(event) ?: return

        val inputName = Messages.showInputDialog(
            project,
            "Enter file name:",
            "Create ${template.title}",
            Messages.getQuestionIcon(),
            template.baseName,
            null
        ) ?: return

        val name = inputName.trim()
            .removeSuffix(".${template.extension}")

        if (!isValidFileName(name)) {
            Messages.showErrorDialog(
                project,
                "Invalid file name.",
                "Nuxt.js Toolkit"
            )
            return
        }

        val outputFileName = try {
            template.fileName(name)
        } catch (exception: Exception) {
            Messages.showErrorDialog(
                project,
                exception.message ?: "Invalid file name.",
                "Nuxt.js Toolkit"
            )
            return
        }

        if (directory.findChild(outputFileName) != null) {
            Messages.showErrorDialog(
                project,
                "File '$outputFileName' already exists.",
                "Nuxt.js Toolkit"
            )
            return
        }

        val content = template.content(name)

        try {
            WriteCommandAction.runWriteCommandAction(
                project,
                "Create $outputFileName",
                null,
                Runnable {
                    // Check again inside the write command.
                    if (directory.findChild(outputFileName) != null) {
                        throw IllegalStateException(
                            "File '$outputFileName' already exists."
                        )
                    }

                    val newFile = directory.createChildData(
                        this,
                        outputFileName
                    )

                    VfsUtil.saveText(newFile, content)
                }
            )
        } catch (exception: Exception) {
            Messages.showErrorDialog(
                project,
                exception.message
                    ?: "Could not create '$outputFileName'.",
                "Nuxt.js Toolkit"
            )
        }
    }

    private fun getTargetDirectory(
        event: AnActionEvent
    ): VirtualFile? {
        val selectedFile =
            event.getData(CommonDataKeys.VIRTUAL_FILE)
                ?: event.getData(CommonDataKeys.PSI_FILE)?.virtualFile
                ?: event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
                    ?.singleOrNull()
                ?: return null

        return if (selectedFile.isDirectory) {
            selectedFile
        } else {
            selectedFile.parent
        }
    }

    private fun isValidFileName(name: String): Boolean {
        if (name.isBlank()) return false

        if (name.startsWith(".") || name.endsWith(".")) {
            return false
        }

        if (name.any { it in "\\/:*?\"<>|" }) {
            return false
        }

        return true
    }
}
