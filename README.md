# Nuxt.js Toolkit

A WebStorm plugin that adds a **Nuxt.js** submenu to:

- Project View context menu
- Editor context menu

The submenu provides ready-to-use templates for common Nuxt.js, Vue, Nitro, and TypeScript files.

## Requirements

- WebStorm 2026.3 or newer
- IntelliJ IDEA 2026.2 or newer
- JDK 21
- Gradle 9.0 or newer
- Internet access for the first Gradle sync (to resolve the IDE and plugin dependencies)

## Build and run

Open this project in IntelliJ IDEA with the Gradle and Plugin DevKit plugins enabled.

Run the plugin in a development instance of WebStorm:

```bash
./gradlew runIde
```

On Windows:

```powershell
.\gradlew.bat runIde
```

If the project does not include a Gradle wrapper yet, create one using an installed Gradle 9.x:

```bash
gradle wrapper --gradle-version 9.0.0
```

Build the installable plugin ZIP:

```bash
./gradlew buildPlugin
```

The generated ZIP file is located under:

```text
build/distributions/
```

To install the plugin in WebStorm:

1. Open `Settings → Plugins`.
2. Click the gear icon.
3. Select `Install Plugin from Disk...`.
4. Choose the generated ZIP file.

## Menu

Right-click a folder in the Project tool window or right-click inside an editor:

`New → Nuxt.js`

Select a template to create the corresponding file.

If you right-click a file, the generated file is placed in the same directory as that file.

## Included templates

### General

- Translate
- Composable
- Enum
- Interface Type
- Middleware
- Plugin

### Server (Nitro)

- Server API
- Server Document
- Server Middleware
- Server Model
- Server Model Schema
- Server Plugins
- Server Route
- Server Task
- Server WebSocket Handler

### State and Types

- Store (Pinia)
- Type
- Utility

### Vue

- Component
- Layout
- Page

## Notes

- Templates are based on Nuxt.js, Vue, Nitro, Pinia, and TypeScript conventions.
- Composable names are automatically formatted with the `use` prefix when needed.
- Store templates automatically generate the `use` prefix for the store function name.
- Interface, document, model, type, and utility templates use the provided file name to generate their corresponding identifiers.
- Templates provide minimal starter code and can be customized to match your project's architecture and coding conventions.
- The plugin currently refuses to overwrite an existing file.
- The `vendor` email in `plugin.xml` is a placeholder. Replace it with your own email before publishing.
