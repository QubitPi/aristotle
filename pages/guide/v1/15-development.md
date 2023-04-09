---
layout: doc-guide
group: guide
title: Development
description: Building and Releasing Aristotle
version: 1
---

{:toc}

Aristotle is developed in [Jersey][Jersey] framework.

---

**NOTE:** In case you are not familiar with Jersey, it is a parallel technology with "Spring Boot framework".
**Aristotle offers absolutely NO support for Spring** and will remain as an exclusive Jersey application in the future, 
because Jersey, alone with its backing technology [HK2][HK2], is the reference-implementation of JSR-370 (and HK2,
JSR-330) _standards_ while Spring is not.

By "having no support for Spring", Aristotle means the following:

1. Aristotle DOES NOT, AND WILL NOT, run as a Spring Boot Webservice
2. Aristotle has ABSOLUTE ZERO direct-dependency from Spring
3. Aristotle runs in NON-SPRING containers, such as Jetty

_Aristotle rejects any conducts that violate the 3 rules above. NO EXCEPTION_.

---

The following guide is intended to help developers who maintain or want to make changes to the Aristotle framework.
           
Building
--------

Aristotle is built using Maven. Because Aristotle is a mono-repo with interdependencies between modules, it is
recommended to fully build and install the project at least once:  

```bash
mvn clean install
```

Thereafter, individual modules can be built whenever making changes to them. For example, the following command would 
rebuild only aristotle-core:

```bash
mvn clean install -f aristotle-core
```

Pull requests and release builds leverage GitHub Action. PR builds simply run the complete build (`mvn -B clean verify`) 
along with code coverage.

Release Versions
----------------

Aristotle follows [semantic versioning][semantic versioning] for its releases. Minor and patch versions only have the 
version components of `MAJOR.MINOR.PATCH`.

Major releases are often pre-released prior to the publication of the final version.  Pre-releases have the format of 
`MAJOR.MINOR.PATCH-prCANDIDATE`.  For example, 5.0.0-pr2 is release candidate 2 of the Aristotle 5.0.0 version.

[HK2]: https://javaee.github.io/hk2/

[Jersey]: https://eclipse-ee4j.github.io/jersey/

[semantic versioning]: https://semver.org/
