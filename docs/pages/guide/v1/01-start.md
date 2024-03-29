---
layout: doc-guide
group: guide
title: Getting Started
description: Getting Started
version: 1
---

Contents
--------

1. Contents
{:toc}

An API for Managing Knowledge Graph Data
----------------------------------------

The easiest way to get started with Aristotle is to use the [Aristotle Basic App Starter][Aristotle Basic App Starter].
The starter bundles all of the dependencies we will need to stand up the simplest GraphQL web service. We will deploy
this example locally.

### Docker

Aristotle Docker is a tool for setting up and running a full-fledged Aristotle instance Docker application. With Docker,
an Aristotle application is started with a single command. **It's the quickest approach to get a taste of Aristotle**.
Aristotle Docker works in all environments: production, staging, development, testing, as well as CI workflows.

#### Get Image

##### Docker Hub

We can pull the image from [Docker Hub](https://hub.docker.com/r/jack20191124/aristotle-examples-basic/):

```bash
docker pull jack20191124/aristotle-examples-basic:latest
```

##### GitHub

We could also build the image from [source][Aristotle Basic App Starter]:

```bash
git clone https://github.com/QubitPi/aristotle.git
cd aristotle/aristotle-examples/aristotle-examples-basic/
docker build -t jack20191124/aristotle-examples-basic .
```

Note that the Dockerfile base image is [11.0.14-jdk11][Jetty 11.0.14-jdk11]. This particular version is chosen so that
it works with JDK 11. [11.0.14-jdk11][Jetty 11.0.14-jdk11] is also the minimum working version known for JDK 11.

#### Standup a Container

When image is on our machine (either by pulling or building), we can spin up the app instance using

```bash
docker run -it -p 80:8080 jack20191124/aristotle-examples-basic
```

#### Firing The First Request

Install [GraphiQL for Mac](https://github.com/skevy/graphiql-app)

```bash
brew install --cask graphiql
```

Then issue the following query at `http://localhost:80/v1/data/graphql`:

```graphql
{
    bookById(id: "book-1") {
        id
        name
        pageCount
    }
}
```

Our first working Aristotle query shows up!

<!-- markdown-link-check-disable -->
![Error loading examples-basic-example-query.png](/aristotle/assets/images/examples-basic-example-query.png){:class="img-fluid"}
<!-- markdown-link-check-enable -->

#### Troubleshooting

If you see 404 query results, one possible place to look at is the Dockerfile version. We might need to upgrade it
from `11.0.14-jdk11` to, for example, `12.0.0-jdk11` if its available. At the time of writing, `11.*.*.jdk11` is the
latest working version for JDK 11.

<!-- markdown-link-check-disable -->
[Aristotle Basic App Starter]: https://github.com/QubitPi/aristotle/tree/master/aristotle-examples/aristotle-examples-basic
<!-- markdown-link-check-enable -->
[Jetty 11.0.14-jdk11]: https://github.com/eclipse/jetty.docker/blob/7cb88c382621890d7fb8d02267e78ae5e7c2d719/eclipse-temurin/11.0/jdk11/Dockerfile

### Extending Docker Application

Happy with Aristotle? You can go further with productionizing Aristotle from here

#### Configuration

If we put our config file into a subfolder of `src/main/resources`, it is important to tell Maven to add those
resources to classpath so that our SystemConfig module could read all downstream configuration value from properties
file. For instance

```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
    </resource>
</resources>
```
