hello-curl
==========

Kotlin/Native test repo for static `libcurl` on Windows\
Based on samples from official Kotlin repo.\
The only difference is adding option to use native certificates:\
`curl_easy_setopt(curl, CURLOPT_SSL_OPTIONS, CURLSSLOPT_NATIVE_CA)`

Resulted executables are available as CI artifact.


### CHANGELOG

#### 2 Jun 2021

* standalone portable .exe builds successfully
