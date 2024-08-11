[![Java Version](https://img.shields.io/badge/java-17+-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

# Super-framework-master

## Features
- API Automation test support
- Mobile Automation test support
- Customize reporting and logging using ExtentReport

## Pre-requisite
- Download and install JDK 17 | [download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Download and install node and npm | [download](https://nodejs.org/en/download)
- Install Appium `npm install -g appium`
- Download and install Appium Inspector | [download](https://github.com/appium/appium-inspector/releases)

## How to Run

### API Tests
- Setup `testng.xml`
- run `mvn test`

### Mobile Tests
- Setup `testng.xml`
- Launch appium and expose port 4723 `appium -p 4723`
- Change `platform` config properties based on your platform on `src/main/resources/Constants.properties` | `Android` or `iOS`
- run `mvn test`