// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.test) apply false
//    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
//    alias(libs.plugins.kotlin.compose) apply false
}

subprojects {
    configurations.all {
        resolutionStrategy.force("com.google.accompanist:accompanist-navigation-animation:0.25.1")
    }
}