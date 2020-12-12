<!--
*** Thanks for checking out this README Template. If you have a suggestion that would
*** make this better, please clone the repo and create a merge request or simply open
*** an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->




<!-- PROJECT LOGO -->
<div align="center">
    <div style="display:flex; flex-direction: row; justify-content:space-around;">
        <img src="static/images/kotlin-icon.svg" alt="Logo">
        <p>+</p>
        <img src="static/images/spring.svg" alt="Logo">
    </div>

<h3 align="center">Coroutines</h3>

  <p align="center">
    Spring boot, Coroutine Implementation
    <br />
    <a href="https://github.com/OmarAyadi/coroutines/issues">Report Bug</a>
    ·
    <a href="https://github.com/OmarAyadi/coroutines/issues">Request Feature</a>
  </p>
</div>


<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- TABLE OF CONTENTS -->

## Table of Contents

* [About the Project](#about-the-project)
    * [Built With](#built-with)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Run](#Run)
* [Folder Structure](#folder-structure)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contributing](#contributing)

<!-- ABOUT THE PROJECT -->

## About The Project

This is a demo project build with [spring-boot][spring-boot-url] and [kotlin][kotlin-url] to present an optimized and
refined coding with coroutines

### Built With

* [Spring-Boot][spring-boot-url]
* [Kotlin][kotlin-url]
* [Mongo][mongo-url]
* [Coroutines][coroutines-url]
* [REST][rest-url]

<!-- GETTING STARTED -->

## Getting Started

This part will help you set up this demo project. To get a local copy up and running follow these simple example steps.

### Prerequisites

* Install [kotlin][kotlin-install-url]

* Install [gradle][gradle-install-url]

* Install [docker][docker-url]

### Run

1. Pull [Mongo Image][mongo-docker-url] from docker hub

```sh
docker pull mongo
```

2. Run mongo

```sh
docker run --name mg -p 27017:27017 -d mongo
```

3. Clone the repo

* with Https

```sh
git clone https://github.com/OmarAyadi/coroutines
```

* with [ssh][ssh-github]

```sh
git clone git@github.com:OmarAyadi/coroutines.git
```

4. Build the project

```sh
gradle clean build
```

5. Run the project

```sh
java -jar build/libs/<jar_name>.jar
```

<!-- PROJECT ARCHITECTURE -->

## Folder Structure

Here's the folder structure for Coroutine project :

```
coroutines/      # Root directory.
|- config/       # Contains config beans and files 
|- dto/          # Contains client request objects representations 
|- global/       # Contains util methods and global variables
|- handlers/     # Contains model handles 
|- repos/        # Contains database representation objects 
|- routers/      # Contains http routes mapping 
|- svc/          # Contains project services
```

<!-- ROADMAP -->

## Roadmap

See the [open issues][repo-issues] for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the projects an amazing place to learn, inspire, and create. Any contributions you make
are **greatly appreciated**.

1. Clone the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[repo]: https://github.com/OmarAyadi/coroutines
[repo-issues]: https://github.com/OmarAyadi/coroutines/issues
[contributors-shield]: https://img.shields.io/github/contributors/OmarAyadi/coroutines.svg?style=flat-square
[contributors-url]: https://github.com/OmarAyadi/coroutines/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/OmarAyadi/coroutines.svg?style=flat-square
[forks-url]: https://github.com/OmarAyadi/coroutines/network/members
[stars-shield]: https://img.shields.io/github/stars/OmarAyadi/coroutines.svg?style=flat-square
[stars-url]: https://github.com/OmarAyadi/coroutines/stargazers
[issues-shield]: https://img.shields.io/github/issues/OmarAyadi/coroutines.svg?style=flat-square
[issues-url]: https://github.com/OmarAyadi/coroutines/issues
[license-shield]: https://img.shields.io/github/license/OmarAyadi/coroutines.svg?style=flat-square
[license-url]: https://github.com/OmarAyadi/coroutines/blob/main/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/mohamedomarayadi
[spring-boot-url]: https://spring.io/projects/spring-boot
[kotlin-url]: https://kotlinlang.org
[kotlin-install-url]: https://kotlinlang.org/docs/tutorials/command-line.html
[coroutines-url]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[mongo-url]: https://www.mongodb.com
[rest-url]: https://restfulapi.net
[gradle-install-url]: https://gradle.org/install
[docker-url]: https://docs.docker.com/get-docker
[mongo-docker-url]: https://hub.docker.com/_/mongo
[ssh-github]: https://docs.github.com/en/enterprise-server@2.20/github/authenticating-to-github/adding-a-new-ssh-key-to-your-github-account