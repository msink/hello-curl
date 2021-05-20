hello-curl
==========

Kotlin/Native test repo for static `libcurl` on Windows\
Based on samples from official Kotlin repo

Curren status:
- standalone portable .exe builded successfully, with some temporary hacks.

Remaining issues:
- https://github.com/msys2/MINGW-packages/pull/8469 not merged,
  so `libcurl.a` and `libssh2.a` are embedded in repo
- `libunistring` linked as .dll, but magically resulted .exe still works!

Resulted executables are available as CI artifacts.
