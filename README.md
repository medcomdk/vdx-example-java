![Build Status](https://github.com/medcomdk/vdx-example-java/workflows/CICD/badge.svg) ![Test Coverage](.github/badges/jacoco.svg)
# vdx-example-java

Simple example on how to call VideoAPI in Java without using WSC proxy. It is a simple Spring Boot application with a 
single endpoint at v1/{uuid} where uuid is a meeting uuid in your organisation.

## Getting started

This is a working example, and you only need to create a JKS Keystore with a private key entry and a trusted certificate
entry. 

The private key entry is the key used to sign requests to the STS and for mTLS. The alias must be `client`. 
It must be configured in the STS. 

The trusted certificate entry must be the signing certificate from the STS. This certificate can be found at 
[https://docs.vconf-stage.dk/sts/cert/sts.cer](https://docs.vconf-stage.dk/sts/cert/sts.cer). The keystore password 
and private key password must be `Test1234`.

The keystore must be named client.jks and located in the same folder as this file.

### Run example in docker

Run below command and open [http://localhost](http://localhost) in a browser, and you can call the service using
Swagger UI. The command must be executed from the same folder as this file, and it is required that docker-compose is 
installed.

```shell
docker-compose -f documentation/docker/compose/docker-compose.yml up
```

### Run it in your IDE

A test application can be found in the class TestApplication.  
