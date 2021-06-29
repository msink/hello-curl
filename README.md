hello-curl
==========

Kotlin/Native test repo for static `libcurl` on Windows\
Based on samples from official Kotlin repo.\
The only difference is adding option to use native certificates:\
`curl_easy_setopt(curl, CURLOPT_SSL_OPTIONS, CURLSSLOPT_NATIVE_CA)`

Resulted executables are available as CI artifact.

To build locally:
- install MSYS2 as described in http://www.msys2.org
- in MSYS2 console install CURL development files:
  `pacman -S mingw-w64-x86_64-curl mingw-w64-i686-curl`

### CHANGELOG

#### 2 Jun 2021

* standalone portable .exe builds successfully


#### 29 Jun 2021

* update Kotlin to 1.5.20 and enable HMPP
