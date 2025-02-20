import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("androidx.compose.material3:material3:1.2.0")
            implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")
        }

    }
}


compose.desktop {
    application {
        mainClass = "com.dallascollege.monopoly.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dallascollege.monopoly"
            packageVersion = "1.0.0"
        }
    }
}

