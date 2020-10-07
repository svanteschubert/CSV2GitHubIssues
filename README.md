# CSV to GitHub issues with labels & milestones

## Overview

The idea is to collect issues in a spreadsheet and save it to a comma-separated-value (CSV) file to import the data as GitHub issues with labels & milestones.
The current downside is that this implementation was based on a very specific spreadsheet structure and there might other structure more useful for your use cases (or in general).
Pull Requests are welcome. :)

**NOTE**: In addition, there is an example of using a [template of GitHub issues](.github/ISSUE_TEMPLATE/Change_request_and_impact_analysis.md), in the .github/ISSUE_TEMPLATE directory.
Aside of it some [beginners markdown help file](.github/markdown.md).

## Functionality

We initially added Csv2GitHub functionality that allows us the initialization of this CEN-TC434-WG1 repository from our [issue list spreadsheet (CSV)](./src/test/resources/issues/issue-list.csv). Creating TC issues including our specific labels and milestones.

**NOTE**: *Whenever a label/milestone is chosen in GitHub GUI, all related issues are being listed. [More advanced search](https://help.github.com/en/github/searching-for-information-on-github/searching-issues-and-pull-requests#search-by-label) is possible.*

## Requisition

* Java 11
* Edit your data list (for instance with LibreOffice) and save the spreadsheet to a [comma separated value text file](./src/test/resources/issues/issue-list.csv)
* Provide a new (e.g. private) GitHub repository - where the new issues are being created into:
    Adjust the [REPO_NAME](./src/test/java/example/github/issues/Github.java#L39) variable using your repository.
* GitHub API access is provided by your GitHub "personal access" token in your user directory in a "~/.github" file, for instance:

```javascript
login=your-github-login-id
oauth=your-personal-access-token-01234567890abcdefgh
```

**NOTE**: *You should create yourself a new "personal access token" with sufficient access rights under:*
    *Login GitHub -> upper right corner -> Settings -> Developer Settings -> "personal access token"*

## How to build & trigger the generation of issues/labels/milestones for a GitHub repository

Call from a command-line in the root directory of the repository: "gradlew clean build run"

**NOTE** The JDK reflection exception in the beginning of execution is known, see [their bug & explanation](https://github.com/hub4j/github-api/issues/754).

## How it works

1. The build system Gradle is executing the [main class "Csv2Github.java"](./src/test/java/example/github/issues/Github.java/Csv2Github.java).
2. The CSV is being parsed line by line by the ["Csv.java" class](./src/test/java/example/github/issues/Csv.java).
3. GitHub is accessed via the ["Github.java" class](./src/test/java/example/github/issues/Github.java) taking advantage of the [Java GitHub API of Kohsuke Kawaguchi](https://github-api.kohsuke.org/).
