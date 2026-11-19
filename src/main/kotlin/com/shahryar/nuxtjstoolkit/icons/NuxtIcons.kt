
package com.shahryar.nuxtjstoolkit.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object NuxtIcons {

    private fun load(name: String): Icon =
        IconLoader.getIcon(
            "/icons/$name.svg",
            NuxtIcons::class.java
        )

    val NUXT = load("nuxt")

    val TRANSLATE = load("translate")
    val COMPOSABLE = load("composable")
    val ENUM = load("enum")
    val INTERFACE = load("interface")
    val MIDDLEWARE = load("shield")
    val PLUGIN = load("puzzle")

    val SERVER = load("server")
    val DATABASE = load("database")
    val ROUTE = load("route")

    val STORE = load("store")
    val TYPE = load("type")
    val UTILITY = load("utility")

    val COMPONENT = load("component")
    val LAYOUT = load("layout")
    val PAGE = load("page")

    val TASK = load("task")
    val DOCUMENT = load("document")

    val SERVER_SHIELD = load("server-guard")
    val MODEL = load("model")
    val SERVER_ROUTE = load("server-route")
    val SCHEMA = load("schema")
    val WEBSOCKET = load("websocket")
}
