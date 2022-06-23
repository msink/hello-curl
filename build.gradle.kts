// SPDX-License-Identifier: MIT

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.7.0"
}

repositories {
    mavenCentral()
}

//val msys2 = File(System.getenv("MSYS2_ROOT") ?: "C:/msys64/")
val msys2 = projectDir.resolve("bundle/2022-06-23")

kotlin {
    mingwX64("mingw64")
    mingwX86("mingw32")

    val commonMain by sourceSets.getting
    val nativeMain by sourceSets.creating {
        dependsOn(commonMain)
        sourceSets.all {
            languageSettings.optIn("kotlinx.cinterop.UnsafeNumber")
        }
    }
    targets.withType<KotlinNativeTarget> {
        sourceSets["${targetName}Main"].apply {
            dependsOn(nativeMain)
        }
        compilations["main"].apply {
            cinterops.create("libcurl") {
                includeDirs.headerFilterOnly(msys2.resolve("${targetName}/include"))
            }
        }
        binaries.executable {
            entryPoint = "curl.main"
            linkerOpts(
                "-L${msys2.resolve("${targetName}/lib")}",
                "-Wl,-Bstatic",
                "-lstdc++",
                "-static",
                "-lcurl",
                "-lidn2",
                "-lssh2",
                "-lpsl",
                "-lbcrypt",
                "-lcrypt32",
                "-lwldap32",
                "-lzstd",
                "-lbrotlidec",
                "-lbrotlicommon",
                "-lz",
                "-lws2_32",
                "-lunistring",
                "-liconv"
            )
        }
    }
}
