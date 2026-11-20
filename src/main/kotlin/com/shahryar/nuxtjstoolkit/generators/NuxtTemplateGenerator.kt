package com.shahryar.nuxtjstoolkit.generators

import com.shahryar.nuxtjstoolkit.icons.NuxtIcons
import javax.swing.Icon

enum class NuxtTemplateCategory {
    GENERAL,
    SERVER,
    STATE_AND_TYPES,
    VUE
}

enum class NuxtNameFormat {
    NONE,
    PASCAL_CASE,
    PASCAL_CASE_BEFORE_DOT,
    COMPOSABLE,
    STORE
}

data class NuxtTemplateDefinition(
    val title: String,
    val baseName: String,
    val extension: String,
    private val templateContent: String,
    val category: NuxtTemplateCategory,
    val icon: Icon? = null,
    private val nameFormat: NuxtNameFormat = NuxtNameFormat.NONE
) {

    fun fileName(customName: String? = null): String {
        val name = customName
            ?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("A file name is required.")

        return "$name.$extension"
    }

    fun content(customName: String? = null): String {
        val name = customName ?: baseName

        val formattedName = when (nameFormat) {
            NuxtNameFormat.NONE -> name

            NuxtNameFormat.PASCAL_CASE ->
                name.toPascalCase()

            NuxtNameFormat.PASCAL_CASE_BEFORE_DOT ->
                name.substringBefore('.').toPascalCase()

            NuxtNameFormat.COMPOSABLE ->
                if (name.take(3).equals("use", ignoreCase = true)) {
                    name
                } else {
                    "use${name.toPascalCase()}"
                }

            NuxtNameFormat.STORE ->
                "use${name.toPascalCase()}"
        }

        return templateContent
            .trimIndent()
            .replace("@@NAME@@", name)
            .replace("@@FORMATTED_NAME@@", formattedName)
            .trimEnd() + "\n"
    }

    private fun String.toPascalCase(): String {
        if (isEmpty()) return this

        return replaceFirstChar {
            it.uppercase()
        }
    }
}

object NuxtTemplateGenerator {

    val templates = listOf(

        // General

        NuxtTemplateDefinition(
            title = "Translate",
            baseName = "translate",
            extension = "json",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.TRANSLATE,
            templateContent = """
                {
                    "en": {

                    },
                    "fa": {

                    }
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Composable",
            baseName = "composable",
            extension = "ts",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.COMPOSABLE,
            nameFormat = NuxtNameFormat.COMPOSABLE,
            templateContent = """
                export const @@FORMATTED_NAME@@ = () => {

                    return {

                    }
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Enum",
            baseName = "enum",
            extension = "ts",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.ENUM,
            templateContent = """
                export enum @@NAME@@ {
                    Item = 1,
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Interface Type",
            baseName = "interface",
            extension = "ts",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.INTERFACE,
            nameFormat = NuxtNameFormat.PASCAL_CASE_BEFORE_DOT,
            templateContent = """
                import type {ObjectId} from "mongoose";

                export interface @@FORMATTED_NAME@@ {
                    _id: ObjectId,
                    name: string,
                    email: string,
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Middleware",
            baseName = "middleware",
            extension = "ts",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.MIDDLEWARE,
            templateContent = """
                export default defineNuxtRouteMiddleware((to, from) => {

                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Plugin",
            baseName = "plugin",
            extension = "ts",
            category = NuxtTemplateCategory.GENERAL,
            icon = NuxtIcons.PLUGIN,
            templateContent = """
                export default defineNuxtPlugin((nuxtApp) => {

                });
            """
        ),

        // Server

        NuxtTemplateDefinition(
            title = "Server API",
            baseName = "api",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.SERVER,
            templateContent = """
                export default defineEventHandler(async (event) => {
                    return 'Hello Nitro'
                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Document",
            baseName = "document",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.DOCUMENT,
            nameFormat = NuxtNameFormat.PASCAL_CASE_BEFORE_DOT,
            templateContent = """
                import type {ObjectId} from "mongoose";

                export interface @@FORMATTED_NAME@@ extends Document {
                    _id: ObjectId,
                    name: string,
                    email: string,
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Middleware",
            baseName = "middleware",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.SERVER_SHIELD,
            templateContent = """
                export default defineEventHandler(async (event) => {

                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Model",
            baseName = "model",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.MODEL,
            nameFormat = NuxtNameFormat.PASCAL_CASE,
            templateContent = """
                import mongoose, { model, Schema } from "mongoose";

                const @@FORMATTED_NAME@@Schema = new Schema({

                });

                export const @@FORMATTED_NAME@@ = mongoose.models.@@FORMATTED_NAME@@
                    || model<@@FORMATTED_NAME@@Document>(
                        '@@FORMATTED_NAME@@',
                        @@FORMATTED_NAME@@Schema
                    );
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Model Schema",
            baseName = "schema",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.SCHEMA,
            templateContent = """
                import {Schema} from "mongoose";

                export const @@NAME@@ = new Schema({

                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Plugins",
            baseName = "plugins",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.PLUGIN,
            templateContent = """
                export default defineNitroPlugin((nitroApp) => {

                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Route",
            baseName = "route",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.SERVER_ROUTE,
            templateContent = """
                export default defineEventHandler(async (event) => {
                    return 'Hello Nitro'
                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server Task",
            baseName = "task",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.TASK,
            templateContent = """
                export default defineTask({
                    meta: {
                        name: "db:migrate",
                        description: "Run database migrations",
                    },
                    run({ payload, context }) {
                        console.log("Running DB migration task...");
                        return { result: "Success" };
                    },
                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Server WebSocket Handler",
            baseName = "websocket",
            extension = "ts",
            category = NuxtTemplateCategory.SERVER,
            icon = NuxtIcons.WEBSOCKET,
            templateContent = """
                export default defineWebSocketHandler({
                    open(peer) {
                        console.log('✅ اتصال WebSocket باز شد', peer);
                        peer.write('خوش آمدید به چت');
                    },
                    message(peer, message) {
                        console.log('📨 پیام جدید:', message.toString());
                        peer.write(`شما گفتید: ${'$'}{message.toString()}`);
                    },
                    close(peer, code, reason) {
                        console.log('❌ اتصال بسته شد', code, reason);
                    }
                });
            """
        ),

        // State and Types

        NuxtTemplateDefinition(
            title = "Store",
            baseName = "store",
            extension = "ts",
            category = NuxtTemplateCategory.STATE_AND_TYPES,
            icon = NuxtIcons.STORE,
            nameFormat = NuxtNameFormat.STORE,
            templateContent = """
                import { defineStore } from 'pinia';

                export const @@FORMATTED_NAME@@ = defineStore('@@NAME@@', () => {

                    return {

                    }
                });
            """
        ),

        NuxtTemplateDefinition(
            title = "Type",
            baseName = "type",
            extension = "ts",
            category = NuxtTemplateCategory.STATE_AND_TYPES,
            icon = NuxtIcons.TYPE,
            nameFormat = NuxtNameFormat.PASCAL_CASE,
            templateContent = """
                export type @@FORMATTED_NAME@@ = {
                    item: string;
                    func: (value: any) => void;
                }
            """
        ),

        NuxtTemplateDefinition(
            title = "Utility",
            baseName = "utility",
            extension = "ts",
            category = NuxtTemplateCategory.STATE_AND_TYPES,
            icon = NuxtIcons.UTILITY,
            nameFormat = NuxtNameFormat.PASCAL_CASE,
            templateContent = """
                export const @@FORMATTED_NAME@@ = (value: any) => {

                }
            """
        ),

        // Vue

        NuxtTemplateDefinition(
            title = "Component",
            baseName = "component",
            extension = "vue",
            category = NuxtTemplateCategory.VUE,
            icon = NuxtIcons.COMPONENT,
            templateContent = """
                <template>
                    <div>

                    </div>
                </template>

                <script lang="ts" setup>

                </script>

                <style>

                </style>
            """
        ),

        NuxtTemplateDefinition(
            title = "Layout",
            baseName = "layout",
            extension = "vue",
            category = NuxtTemplateCategory.VUE,
            icon = NuxtIcons.LAYOUT,
            templateContent = """
                <template>
                    <div>
                        <slot />
                    </div>
                </template>

                <script lang="ts" setup>

                </script>

                <style>

                </style>
            """
        ),

        NuxtTemplateDefinition(
            title = "Page",
            baseName = "page",
            extension = "vue",
            category = NuxtTemplateCategory.VUE,
            icon = NuxtIcons.PAGE,
            templateContent = """
                <template>
                    <div>

                    </div>
                </template>

                <script lang="ts" setup>

                </script>

                <style>

                </style>
            """
        )
    )
}
