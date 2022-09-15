// SPDX-License-Identifier: MIT

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform") version "1.7.10"
}

repositories {
    mavenCentral()
}

val msys2root =
    if (HostManager.hostOs() == "windows")
        File(System.getenv("MSYS2_ROOT") ?: "C:/msys64/")
    else
        projectDir.resolve("bundle/2022-08-31")

kotlin {
    mingwX64("mingw64")
    mingwX86("mingw32")

    val commonMain by sourceSets.getting
    val nativeMain by sourceSets.creating {
        dependsOn(commonMain)
    }
    targets.withType<KotlinNativeTarget> {
        sourceSets["${targetName}Main"].apply {
            dependsOn(nativeMain)
        }

        val sysroot = msys2root.resolve(targetName)
        val systemInclude = sysroot.resolve("include")
        val systemLib = sysroot.resolve("lib")

        compilations["main"].apply {
            cinterops.create("libcurl") {
                includeDirs.headerFilterOnly(systemInclude)
            }
            sourceSets.all {
                languageSettings.optIn("kotlinx.cinterop.UnsafeNumber")
            }
        }

        binaries.executable {
            entryPoint = "curl.main"

            if (targetName.startsWith("mingw")) {
                fun lib(foo: String) = "${systemLib}/lib${foo}.a"
                linkerOpts(
                    //fully static linking
                    "-Wl,-Bstatic", "-lstdc++", "-static",

                    //from MSYS2 distribution:
                    lib("curl"),
                    lib("ssh2"),
                    lib("idn2"),
                    lib("psl"),
                    lib("brotlidec"),
                    lib("brotlicommon"),
                    lib("unistring"),
                    lib("zstd"),

                    //from K/N distribution:
                    "-lbcrypt",
                    "-lcrypt32",
                    "-liconv",
                    "-lwldap32",
                    "-lws2_32",
                    "-lz",
                )
            }
        }
    }
}
