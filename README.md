<img src="https://raw.githubusercontent.com/Blamer-io/blamer/main/blamer-bot.svg" width="150" alt="blamer-bot"/>

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/blamer-io/bot)](https://www.rultor.com/p/Blamer-io/bot)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
<br>

[![mvn](https://github.com/Blamer-io/bot/actions/workflows/mvn.yaml/badge.svg)](https://github.com/Blamer-io/bot/actions/workflows/mvn.yaml)
[![docker](https://github.com/Blamer-io/bot/actions/workflows/docker.yaml/badge.svg)](https://github.com/Blamer-io/bot/actions/workflows/docker.yaml)
[![docker.io](https://img.shields.io/docker/v/l3r8y/blamer-bot/latest)](https://hub.docker.com/repository/docker/l3r8y/blamer-bot/general)
[![codecov](https://codecov.io/gh/Blamer-io/bot/branch/master/graph/badge.svg?token=CC9UR3TRCW)](https://codecov.io/gh/Blamer-io/bot)

[![Hits-of-Code](https://hitsofcode.com/github/Blamer-io/bot)](https://hitsofcode.com/view/github/Blamer-io/bot)
[![Lines-of-Code](https://tokei.rs/b1/github/Blamer-io/bot)](https://github.com/Blamer-io/bot)
[![PDD status](http://www.0pdd.com/svg?name=Blamer-io/bot)](http://www.0pdd.com/p?name=Blamer-io/bot)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/Blamer-io/bot/blob/master/LICENSE.txt)

Project architect: [@l3r8yJ](https://github.com/l3r8yJ)

A Telegram [@blamer_io_bot](https://t.me/blamer_io_bot) that redirects your GitHub notifications

**Motivation**. We are needed some UI for blamer, chatbot is the most suitable solution for us, here just in Telegram. 

**Principles**. These are the [design principles](https://www.elegantobjects.org/#principles) behind blamer bot.

**How to use**. [Bot](https://t.me/blamer_io_bot) accepts these types of commands:
```text
/start - Just start command
/token - Provide GitHub token with notifications access
/registry - Token authentication and receiving notifications from GitHub
```

## How to Contribute

Fork repository, make changes, send us a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install
```

You will need Maven 3.8.7+ and Java 17+.

Our [rultor image](https://github.com/eo-cqrs/eo-kafka-rultor-image) for CI/CD.
