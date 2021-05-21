plugins {
    kotlin("multiplatform") version "1.5.0"
}

repositories {
    mavenCentral()
}

val mingw64Path = File(System.getenv("MINGW64_DIR") ?: "C:/msys64/mingw64")

kotlin {
    mingwX64("native") {
        compilations["main"].apply {
            cinterops.create("libcurl") {
                includeDirs.headerFilterOnly(mingw64Path.resolve("include"))
            }
        }
        binaries.executable {
            entryPoint = "curl.main"
            linkerOpts(
                "-L${projectDir}",
                "-L${mingw64Path.resolve("lib")}",
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
