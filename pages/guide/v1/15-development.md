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

Release Process
---------------

1. [Comprehensively test][release test]
2. [Build and push new release tag][release tag]
5. Bump Aristotle version to the new release version
6. Push Aristotle to [GitHub Packages][release packages]
7. Publish [documentation](#messier-61-documentation) to GitHub Pages

### Documentations

Javadoc will be auto-generated and placed under `docs/pages/apidocs/`, then github actions will push everything onto
gh-pages branch



### Messier-61 Documentation

[GitHub Actions][GitHub Actions] allow us to automate, customize, and execute our software development workflows right
in our repository. This also applies to our documentations.

Messier-61 documentation source resides in the master branch under [docs/][Documentation source root] directory

The CI/CD for documentation achieves 2 goals:

1. When a new pull request is made to `master`, there's an action that ensures the site builds successfully, without
   actually deploying. This GitHub workflow job is called `test-doc-build`.
2. When a pull request is merged to the `master` branch, it will be built and deployed to the `gh-pages` branch. After
   that, the new build output will be served on the GitHub Pages site. This job is `deploy-documentation` called deploy.

:::info

The documentation build is a 2-step process:

1. A regular [Docusaurus `build`][Docusaurus Build] command that generates the static HTML of
   [documentation site][documentation]
2. An execution of TypeDoc Node API that generates the [Messier-61 API documentation][documentation on API]

The output of both of the 2 steps above will be picked up and pushed to GitHub Pages for serving.

:::

[HK2]: https://javaee.github.io/hk2/

[Jersey]: https://eclipse-ee4j.github.io/jersey/

[semantic versioning]: https://semver.org/

[release packages]: https://github.com/QubitPi?tab=packages&repo_name=aristotle
[release tag]: https://github.com/QubitPi/aristotle/blob/initial-release/.github/tag-for-release.bash
[release test]: https://github.com/QubitPi/aristotle/blob/master/.github/workflows/test.yml
