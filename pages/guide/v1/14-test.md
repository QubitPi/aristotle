---
layout: doc-guide
group: guide
title: Test
description: Testing
version: 1
---

{:toc}

Groovy Spock
------------

We're _big_ believers in testing our code, both for correctness, as well as to ensure that changes don't unintentionally
break existing contracts unintentionally. For example, we rely heavily on the [Spock][Spock]
framework for our backend service tests, and see a lot of benefit from it's conciseness, built-in [mocking framework]
[mocking framework], and the fact that it uses [Groovy][Groovy]. :smile:

We also strive for very high-quality code, with the belief that quality code is easier to maintain, easier to
understand, and has fewer bugs. To help keep the quality bar high. For instance we have an automated style checker
([Checkstyle][Checkstyle]) in our Maven-based projects with rules that _should_ catch most of the common style issues.

The testing utilizes the following dependencies to enable Groovy Spock testing in Airstotle:

- [spock-bom][BOM]
- [Groovy][Groovy]
- gmavenplus-plugin, which enables Maven lifecycle to scan and pick up, compile, and run relevant test files

All test dependencies are defined in the [top-level POM file][top-level POM] by following the
[official setup](https://github.com/spockframework/spock-example/blob/master/pom.xml):

> Note that the combination of Groovy and Spock versions are important because [some versions have been reported to be
> incompatible with each other](https://stackoverflow.com/a/53973345)

```xml
<project>

    ...

    <properties>
        <version.groovy>4.0.6</version.groovy>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Testing -->
            <dependency>
                <groupId>org.spockframework</groupId>
                <artifactId>spock-bom</artifactId>
                <version>2.4-M1-groovy-4.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.groovy</groupId>
                <artifactId>groovy</artifactId>
                <version>${version.groovy}</version>
            </dependency>
            <dependency> <!-- Enable mocking of non-interface types -->
                <groupId>cglib</groupId>
                <artifactId>cglib-nodep</artifactId>
                <version>3.3.0</version>
            </dependency>
            <dependency> <!-- enables mocking of classes without default constructor (together with CGLIB) -->
                <groupId>org.objenesis</groupId>
                <artifactId>objenesis</artifactId>
                <version>3.0.1</version>
                <scope>test</scope>
            </dependency>
            <dependency> <!-- only necessary if Hamcrest matchers are used -->
                <groupId>org.hamcrest</groupId>
                <artifactId>hamcrest-all</artifactId>
                <version>1.3</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Testing -->
        <dependency>
            <groupId>org.spockframework</groupId>
            <artifactId>spock-core</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.groovy</groupId>
            <artifactId>groovy</artifactId>
        </dependency>
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib-nodep</artifactId>
        </dependency>
        <dependency>
            <groupId>org.objenesis</groupId>
            <artifactId>objenesis</artifactId>
        </dependency>
    </dependencies>

    <build>
        <pluginManagement>
            <plugins>
                <!-- Groovy Spock -->
                <plugin>
                    <groupId>org.codehaus.gmavenplus</groupId>
                    <artifactId>gmavenplus-plugin</artifactId>
                    <version>2.1.0</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>compile</goal>
                                <goal>compileTests</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>

                <!-- Unite Test -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>${version.maven.surefire.plugin}</version>
                    <configuration>
                        <systemPropertyVariables>
                            <java.awt.headless>true</java.awt.headless>
                        </systemPropertyVariables>
                        <includes>
                            <include>%regex[.*Spec.*]</include>
                        </includes>
                        <excludes>
                            <exclude>%regex[.*ITSpec.*]</exclude>
                        </excludes>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-report-plugin</artifactId>
                    <version>${version.maven.surefire.report.plugin}</version>
                </plugin>

                <!-- Integration Test -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-failsafe-plugin</artifactId>
                    <version>${version.maven.failsafe.plugin}</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>integration-test</goal>
                                <goal>verify</goal>
                            </goals>
                        </execution>
                    </executions>
                    <configuration>
                        <includes>
                            <include>**/*ITSpec.*</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>

        <plugins>
            <!-- Mandatory plugins for using Spock -->
            <plugin>
                <groupId>org.codehaus.gmavenplus</groupId>
                <artifactId>gmavenplus-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    ...
</project>
```

> It is also important to avoid transitive dependencies from overriding our own dependency declarations by using Mevne's
> `exclusion` tag. For example
>
> ```xml
> <dependency>
>     <groupId>io.rest-assured</groupId>
>     <artifactId>rest-assured</artifactId>
>     <version>5.3.0</version>
>     <scope>test</scope>
>     <exclusions>
>         <exclusion>
>             <groupId>org.apache.groovy</groupId>
>             <artifactId>groovy</artifactId>
>         </exclusion>
>     </exclusions>
> </dependency>
> ```
>
> We can check for conflicting transitive dependencies, for example, using dependency analyzer plugin in IntelliJ:
>
> ![Error loading dependency-analysis.png](/aristotle/assets/images/dependency-analysis.png){:class="img-responsive"}

[BOM]: (https://qubitpi.github.io/jersey-guide/2022/09/05/maven-bom.html)

[Checkstyle]: http://checkstyle.sourceforge.net/

[Groovy]: http://www.groovy-lang.org/

[mocking framework]: http://spockframework.org/spock/docs/1.1-rc-2/interaction_based_testing.html

[REST Assured]: https://rest-assured.io/

[Spock]: http://spockframework.org/

[top-level POM]: https://github.com/QubitPi/aristotle/blob/master/pom.xml
