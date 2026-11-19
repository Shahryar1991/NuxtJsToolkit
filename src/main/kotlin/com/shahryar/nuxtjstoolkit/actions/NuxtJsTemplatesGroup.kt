package com.shahryar.nuxtjstoolkit.actions

import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.shahryar.nuxtjstoolkit.icons.NuxtIcons

class NuxtJsTemplatesGroup: DefaultActionGroup("Nuxt.js", true) {
    init {
        templatePresentation.icon = NuxtIcons.NUXT

        // General
        add(CreateNuxtFileAction("Translate.ts", "Translate", NuxtIcons.TRANSLATE))
        add(CreateNuxtFileAction("Composable.ts", "Composable", NuxtIcons.COMPOSABLE))
        add(CreateNuxtFileAction("Enum.ts", "Enum", NuxtIcons.ENUM))
        add(CreateNuxtFileAction("Interface Type.ts", "Interface Type", NuxtIcons.INTERFACE))
        add(CreateNuxtFileAction("Middleware.ts", "Middleware", NuxtIcons.MIDDLEWARE))
        add(CreateNuxtFileAction("Plugin.ts", "Plugin", NuxtIcons.PLUGIN))

        addSeparator()

        // Server
        add(CreateNuxtFileAction("Server API.ts", "Server API", NuxtIcons.SERVER))
        add(CreateNuxtFileAction("Server Document.ts", "Server Document", NuxtIcons.DOCUMENT))
        add(CreateNuxtFileAction("Server Middleware.ts", "Server Middleware", NuxtIcons.SERVER_SHIELD))
        add(CreateNuxtFileAction("Server Model.ts", "Server Model", NuxtIcons.MODEL))
        add(CreateNuxtFileAction("Server Model Schema.ts", "Server Model Schema", NuxtIcons.SCHEMA))
        add(CreateNuxtFileAction("Server Plugins.ts", "Server Plugins", NuxtIcons.PLUGIN))
        add(CreateNuxtFileAction("Server Route.ts", "Server Route", NuxtIcons.SERVER_ROUTE))
        add(CreateNuxtFileAction("Server Task.ts", "Server Task", NuxtIcons.TASK))
        add(CreateNuxtFileAction("Server WebSocket Handler.ts", "Server WebSocket Handler", NuxtIcons.WEBSOCKET))

        addSeparator()

        // State and Types
        add(CreateNuxtFileAction("Store.ts", "Store", NuxtIcons.STORE))
        add(CreateNuxtFileAction("Type.ts", "Type", NuxtIcons.TYPE))
        add(CreateNuxtFileAction("Utility.ts", "Utility", NuxtIcons.UTILITY))

        addSeparator()

        // Vue
        add(CreateNuxtFileAction("Component.vue", "Component", NuxtIcons.COMPONENT))
        add(CreateNuxtFileAction("Layout.vue", "Layout", NuxtIcons.LAYOUT))
        add(CreateNuxtFileAction("Page.vue", "Page", NuxtIcons.PAGE))
    }
}