pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url =
                uri("https://build-new.zohocorp.com/zoho_android/aksheetview/milestones/aksheetview_latest_3/AKSHEETVIEW_1.0.0-alpha2/")
            credentials {
                username = System.getProperty("org.gradle.zandroid.buildUser")
                password = System.getProperty("org.gradle.zandroid.buildPassword")
            }
        }
        maven {
            url =
                uri("https://build.zohocorp.com/zoho_android/list_component/milestones/main_2.0/LIST_COMPONENT_1.0.0-beta3")
            credentials {
                username = System.getProperty("org.gradle.zandroid.buildUser")
                password = System.getProperty("org.gradle.zandroid.buildPassword")
            }
        }
    }
}

rootProject.name = "SheetViewTestApp"
include(":app")
 