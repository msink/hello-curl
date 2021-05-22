// SPDX-License-Identifier: MIT

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.5.0"
}

repositories {
    mavenCentral()
}

val msys2 = File(System.getenv("MSYS2_ROOT") ?: "C:/msys64/")

kotlin {
    mingwX64("mingw64")

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
                "-L${projectDir}",
                "-L${msys2.resolve("${targetName}/lib")}",
                "-Wl,-Bstatic",
                "-lstdc++",
                "-static",
                "-lcurl-winssl",
                "-lssh2-wincng",
                "-ladvapi32",
                "-lbcrypt",
                "-lbrotlidec-static",
                "-lbrotlicommon-static",
                "-lcrypt32",
                "-lidn2",
                "-lpsl",
                "-l:libunistring.dll.a",
                "-liconv",
                "-lwldap32",
                "-lws2_32",
                "-lz",
                "-lzstd"
            )
        }
    }
}
