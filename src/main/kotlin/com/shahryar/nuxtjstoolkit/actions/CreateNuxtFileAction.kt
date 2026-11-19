package com.shahryar.nuxtjstoolkit.actions

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.openapi.vfs.VirtualFile
import java.util.Properties
import javax.swing.Icon

class CreateNuxtFileAction(
    private val templateName: String,
    private val title: String,
    icon: Icon? = null
) : AnAction(title, null, icon), DumbAware {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(event: AnActionEvent) {
        val project = event.project
        val directory = getTargetDirectory(event)

        event.presentation.isEnabledAndVisible =
            project != null &&
                    directory != null &&
                    directory.isWritable
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val directory = getTargetDirectory(event) ?: return

        val manager = FileTemplateManager.getInstance(project)

        // Find template
        val template = try {
            manager.getInternalTemplate(templateName)
        } catch (e: Exception) {
            Messages.showErrorDialog(
                project,
                "Template '$templateName' was not found.",
                "Nuxt.js Toolkit"
            )
            return
        }

        // Get extension from the actual template
        val extension = template.extension

        if (extension.isBlank()) {
            Messages.showErrorDialog(
                project,
                "Template '$templateName' does not have a valid file extension.",
                "Nuxt.js Toolkit"
            )
            return
        }

        // Ask for file name
        val inputName = Messages.showInputDialog(
            project,
            "Enter file name:",
            "Create $title",
            Messages.getQuestionIcon(),
            "",
            null
        ) ?: return

        var name = inputName.trim()

        // Remove extension if user entered it manually
        name = name.removeSuffix(".$extension")

        if (!isValidFileName(name)) {
            Messages.showErrorDialog(
                project,
                "Invalid file name.",
                "Nuxt.js Toolkit"
            )
            return
        }

        val fileName = "$name.$extension"

        // Check duplicate
        if (directory.findFile(fileName) != null) {
            Messages.showErrorDialog(
                project,
                "File '$fileName' already exists in this directory.",
                "Nuxt.js Toolkit"
            )
            return
        }

        // Copy default properties instead of modifying shared properties
        val properties = Properties().apply {
            putAll(manager.defaultProperties)
            setProperty("NAME", name)
        }

        try {
            WriteCommandAction.runWriteCommandAction(
                project,
                "Create $fileName",
                null,
                Runnable {
                    // Recheck inside write command to avoid duplicate creation
                    if (directory.findFile(fileName) != null) {
                        throw IllegalStateException(
                            "File '$fileName' already exists."
                        )
                    }

                    FileTemplateUtil.createFromTemplate(
                        template,
                        name,
                        properties,
                        directory
                    )
                }
            )

        } catch (e: Exception) {
            Messages.showErrorDialog(
                project,
                e.message ?: "Could not create '$fileName'.",
                "Nuxt.js Toolkit"
            )
        }
    }

    private fun getTargetDirectory(
        event: AnActionEvent
    ): PsiDirectory? {

        val project = event.project ?: return null

        // Collect possible selected files from the current context
        val candidates = buildList<VirtualFile> {

            event.getData(CommonDataKeys.VIRTUAL_FILE)
                ?.let(::add)

            event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
                ?.let(::addAll)

            event.getData(CommonDataKeys.PSI_FILE)
                ?.virtualFile
                ?.let(::add)
        }

        val selectedFile = candidates.firstOrNull() ?: return null

        val targetDirectory = if (selectedFile.isDirectory) {
            selectedFile
        } else {
            selectedFile.parent ?: return null
        }

        return PsiManager.getInstance(project)
            .findDirectory(targetDirectory)
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