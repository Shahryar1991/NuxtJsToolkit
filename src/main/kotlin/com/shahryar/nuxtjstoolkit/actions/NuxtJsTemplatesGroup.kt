
package com.shahryar.nuxtjstoolkit.actions

import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.shahryar.nuxtjstoolkit.generators.NuxtTemplateCategory
import com.shahryar.nuxtjstoolkit.generators.NuxtTemplateGenerator
import com.shahryar.nuxtjstoolkit.icons.NuxtIcons

class NuxtJsTemplatesGroup : DefaultActionGroup("Nuxt.js", true) {

    init {
        templatePresentation.icon = NuxtIcons.NUXT

        addTemplates(NuxtTemplateCategory.GENERAL)

        addSeparator()

        addTemplates(NuxtTemplateCategory.SERVER)

        addSeparator()

        addTemplates(NuxtTemplateCategory.STATE_AND_TYPES)

        addSeparator()

        addTemplates(NuxtTemplateCategory.VUE)
    }

    private fun addTemplates(category: NuxtTemplateCategory) {
        NuxtTemplateGenerator.templates
            .filter { it.category == category }
            .forEach { template ->
                add(CreateNuxtFileAction(template))
            }
    }
}
