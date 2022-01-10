# CashFX
This application allows you to manage bank accounts by multiple users. You can make transfers, open deposits and take a loan.

## Requirements
- Java 17
- Maven

## How to run
There are two option to start this application:
- With precompiled image
- With Maven

### Precompiled image
Download the version compatible with your OS (Windows or Linux available), unzip it and run `cashfx` or `cashfx.bat` file in the `bin` folder.
You don't have to have Java or Maven on your computer.

### Maven
1. Open terminal in project directory
2. Run `./mvnw clean install` (Linux/MacOS) or `/mvnw.cmd clean install` (Windows)
3. Run `./mvnw -f desktop/pom.xml javafx:run` (Linux/MacOS) or `/mvnw.cmd -f desktop/pom.xml javafx:run` (Windows)

## How to use
First you need to create a new user by providing username and password. Then you can login to your account and create new bank account. Choose the name
and account type. Better accounts have lower fees. After opening your newly created account you can begin to manage it by choosing the available options.
