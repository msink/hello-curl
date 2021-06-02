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
                "-L${msys2.resolve("${targetName}/lib")}",
                "-Wl,-Bstatic",
                "-lstdc++",
                "-static",
                "-lcurl",
                "-lnghttp2",
                "-lidn2",
                "-lssh2",
                "-lpsl",
                "-ladvapi32",
                "-lcrypt32",
                "-lssl",
                "-lcrypto",
                "-lgdi32",
                "-lwldap32",
                "-lzstd",
                "-lbrotlidec-static",
                "-lbrotlicommon-static",
                "-lz",
                "-lws2_32",
                "-lunistring",
                "-liconv"
            )
        }
    }
}
