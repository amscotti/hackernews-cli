pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.3.20"
        kotlin("plugin.serialization") version "2.3.20"
        id("org.graalvm.buildtools.native") version "0.10.5"
        id("com.gradleup.shadow") version "9.4.1"
    }
}

rootProject.name = "hackernews-cli"
