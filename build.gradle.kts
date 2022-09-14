// SPDX-License-Identifier: MIT

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.7.10"
}

repositories {
    mavenCentral()
}

val msys2 = File(System.getenv("MSYS2_ROOT") ?: "C:/msys64/")
//val msys2 = projectDir.resolve("bundle/2022-08-31")

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

            fun lib(foo: String) = "${msys2}/${targetName}/lib/lib${foo}.a"
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
