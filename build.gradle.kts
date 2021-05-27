// SPDX-License-Identifier: MIT

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.5.10"
}

repositories {
    mavenCentral()
}

val msys2 = File(System.getenv("MSYS2_ROOT") ?: "C:/msys64/")

kotlin {
    mingwX64("mingw64")
    mingwX86("mingw32")

    targets.withType<KotlinNativeTarget> {
        sourceSets["${targetName}Main"].apply {
            kotlin.srcDir("src/nativeMain/kotlin")
        }
        compilations["main"].apply {
            cinterops.create("libcurl") {
                includeDirs.headerFilterOnly(msys2.resolve("${targetName}/include"))
            }
        }
        binaries.executable {
            entryPoint = "curl.main"
            linkerOpts(
                "-L${projectDir.resolve("lib/${targetName}")}",
                "-L${msys2.resolve("${targetName}/lib")}",
                "-Wl,-Bstatic",
                "-lstdc++",
                "-static",
                "-lcurl",
                "-lssh2",
                "-ladvapi32",
                "-lbcrypt",
                "-lbrotlidec-static",
                "-lbrotlicommon-static",
                "-lcrypt32",
                "-lidn2",
                "-lpsl",
                "-lunistring",
                "-liconv",
                "-lwldap32",
                "-lws2_32",
                "-lz",
                "-lzstd"
            )
        }
    }
}
