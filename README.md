hello-curl
==========

Kotlin/Native test repo for static `libcurl` on Windows\
Based on samples from official Kotlin repo.

Resulted executables are available as CI artifact.

To build locally: just `./gradlew build`


### CHANGELOG

#### 2 Jun 2021

* standalone portable .exe builds successfully


#### 29 Jun 2021

* update Kotlin to 1.5.20 and enable HMPP


#### 23 Jul 2021

* switch back to `curl-winssl` variant


#### 23 Jun 2022

* update Kotlin to 1.7.0
* switch to bundled libs (for cross-compilation on Linux and Mac hosts)
