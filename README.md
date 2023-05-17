[Aristotle][Aristotle Home] <sup>![Java Version Badge][Java Version Badge]</sup>
=================================================================================

[![GitHub workflow status][GitHub Workflow Status]](https://github.com/QubitPi/aristotle/actions/workflows/ci-cd.yml)
![Last commit][Last Commit]
[![Discord][Discord]](https://discord.com/widget?id=1093089427229790278&theme=dark)
[![License Badge][License Badge]](https://www.apache.org/licenses/LICENSE-2.0)

<a href="https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle"><img align="left" width="17%" alt="SonarCloud" src="https://sonarcloud.io/api/project_badges/quality_gate?project=QubitPi_aristotle"></a>

[![Bugs][Sonar Bugs]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Vulnerabilities][Sonar Vulnerabilities]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Security Rating][Sonar Security Rating]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)

[![Coverage][Sonar Coverage]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Code Smells][Sonar Code Smells]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Maintainability Rating][Sonar Maintainability Rating]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)

[![Lines of Code][Sonar Lines of Code]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Duplicated Lines (%)][Sonar Duplicated Lines]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Reliability Rating][Sonar Reliability Rating]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)
[![Technical Debt][Sonar Technical Debt]](https://sonarcloud.io/summary/new_code?id=QubitPi_aristotle)

Aristotle is a Java library that lets you set up **GraphQL** webservice with minimal effort. Aristotle is meant to be
specialized on querying **knowledge graph** data.

<a href="https://www.bilibili.com/video/BV1oU4y1P7yd">
    <img align="right" width="45%" alt="心海" src="https://raw.githubusercontent.com/QubitPi/QubitPi/master/img/aristotle/kokomi.png">
</a>

Aristotle has **first-class support for [Neo4j][Neo4j] and [Memgraph][Memgraph] databases** for graph data storage
back-ends, but Aristotle's flexible pipeline-style architecture can handle nearly any back-end for data storage, such as
[ArangoDB][ArangoDB].

Quick Start
-----------

Aristotle comes with a pre-configured [example application][Start Tutorial] to help you get started and serve as a
jumping-off-point for building your own web service using Aristotle.

Features
--------

### Storage Abstraction

**One of the design principles of Aristotle is to abstract lower layers of storage away from the administrators and
applications**. Data is exposed and managed as a graph of objects through GraphQL API. They do not have to perform
lower-level storage functions like constructing and managing logical volumes to utilize disk capacity or setting RAID
levels to deal with disk failure.

### Programmatic Data Management

Aristotle provides programmatic interfaces to allow applications to manipulate data. At the base level, this includes
create, read, and delete (CRUD) functions for basic read, write and delete operations. The API implementations are
GraphQL-based, allowing the use of many standard HTTP calls.

Documentation
-------------

More information about Aristotle can be found [here](https://qubitpi.github.io/aristotle/)

Binaries (How to Get It) <sup>[![GitHub Workflow Status][GitHub Workflow Status sup]](https://github.com/QubitPi/aristotle/actions/workflows/ci-cd.yml)</sup>
------------------------

Binaries for Aristotle are stored in [GitHub Packages][Aristotle GitHub Packages]. To install the packages from there,
edit the [pom.xml][POM Intro] file to include the package as a dependency. Dependency information for each Aristotle
sub-module can be found at their corresponding package page. For example:

```xml
<dependency>
    <groupId>com.qubitpi.aristotle</groupId>
    <artifactId>aristotle-core</artifactId>
    <version>x.y.z</version>
</dependency>
```

Next, include the following snippet in the project's POM

```xml
<project>
    ...

    <repositories>
        <repository>
            <id>download-from-github-qubitpi</id>
            <name>Download QubitPi's GitHub Packages</name>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>https://maven.pkg.github.com/QubitPi/aristotle</url>
        </repository>
    </repositories>
</project>
```

Lastly, we would need an access token to install Aristotle packages. Aristotle uses a personal access token (PAT), with
`packages:read` scope, to authenticate to GitHub Packages. Your project can authenticate to GitHub Packages with Apache
Maven by editing your `~/.m2/settings.xml` file to include the personal access token:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <activeProfiles>
        <activeProfile>download-from-github-qubitpi</activeProfile>
    </activeProfiles>

    <profiles>
        <profile>
            <id>download-from-github-qubitpi</id>
            <repositories>
                <repository>
                    <id>download-from-github-qubitpi</id>
                    <url>https://maven.pkg.github.com/qubitpi/aristotle</url>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <servers>
        <server>
            <id>download-from-github-qubitpi</id>
            <username>anybody</username>
            <!-- https://stackoverflow.com/a/64443958/14312712 -->
            <password>a personal access token with at least packages:read scope</password>
        </server>
    </servers>
</settings>
```

License
-------

The use and distribution terms for [Aristotle][Aristotle Home] are covered by the
[Apache License, Version 2.0][Apache License, Version 2.0].

<div align="center">
    <a href="https://opensource.org/licenses">
        <img align="center" width="50%" alt="License Illustration" src="https://github.com/QubitPi/QubitPi/blob/master/img/apache-2.png?raw=true">
    </a>
</div>

[Apache License, Version 2.0]: http://www.apache.org/licenses/LICENSE-2.0.html
[ArangoDB]: https://www.arangodb.com/
[Aristotle Home]: https://qubitpi.github.io/aristotle/
[Aristotle GitHub Packages]: https://github.com/QubitPi?tab=packages&repo_name=aristotle

[Discord]: https://img.shields.io/discord/1093089427229790278?logo=discord&logoColor=white&style=for-the-badge

[GitHub Workflow Status]: https://img.shields.io/github/actions/workflow/status/QubitPi/aristotle/ci-cd.yml?branch=master&logo=github&style=for-the-badge
[GitHub Workflow Status sup]: https://img.shields.io/github/actions/workflow/status/QubitPi/aristotle/ci-cd.yml?branch=master&logo=github&style=flat-square

[Java Version Badge]: https://img.shields.io/badge/Java-11-brightgreen?style=flat-square&logo=OpenJDK&logoColor=white

[Last Commit]: https://img.shields.io/github/last-commit/QubitPi/aristotle/master?logo=github&style=for-the-badge
[License Badge]: https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white

[Memgraph]: https://memgraph.com/

[Neo4j]: https://neo4j.com/product/neo4j-graph-database

[POM Intro]: https://maven.apache.org/guides/introduction/introduction-to-the-pom.html

[Sonar Bugs]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=bugs
[Sonar Code Smells]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=code_smells
[Sonar Coverage]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=coverage
[Sonar Duplicated Lines]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=duplicated_lines_density
[Sonar Lines of Code]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=ncloc
[Sonar Maintainability Rating]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=sqale_rating
[Sonar Reliability Rating]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=reliability_rating
[Sonar Security Rating]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=security_rating
[Sonar Technical Debt]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=sqale_index
[Sonar Vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=QubitPi_aristotle&metric=vulnerabilities

[Start Tutorial]: https://qubitpi.github.io/aristotle/pages/guide/v1/01-start.html
