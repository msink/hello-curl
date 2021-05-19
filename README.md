```
> Task :cinteropLibcurlNative
> Task :compileKotlinNative
> Task :copyCinteropLibcurlNative

> Task :linkDebugExecutableNative FAILED
e: clang++ invocation reported errors

The clang++ command returned non-zero exit code: 1.
output:
ld: f:\msys64\mingw64\lib\libidn2.a(lookup.o):(.text+0x709): undefined reference to `__imp_uninorm_nfc'
ld: f:\msys64\mingw64\lib\libidn2.a(idna.o):(.text+0xaf): undefined reference to `__imp_uninorm_nfc'
ld: f:\msys64\mingw64\lib\libidn2.a(idna.o):(.text+0x38b): undefined reference to `__imp_UC_CATEGORY_M'
ld: f:\msys64\mingw64\lib\libidn2.a(idna.o):(.text+0x3c0): undefined reference to `__imp_uninorm_nfc'
ld: f:\msys64\mingw64\lib\libpsl.a(psl.c.obj):(.text+0x128b): undefined reference to `__imp_uninorm_nfkc'
ld: f:\msys64\mingw64\lib\libpsl.a(psl.c.obj):(.text+0x1307): undefined reference to `__imp_uninorm_nfkc'
clang++: error: linker command failed with exit code 1 (use -v to see invocation)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':linkDebugExecutableNative'.
> Compilation finished with errors

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 13s
4 actionable tasks: 4 executed
```