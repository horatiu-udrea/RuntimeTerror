### Backend server
Build with [Kotlin](https://kotlinlang.org/) using mainly [Ktor](https://ktor.io/) and [Exposed](https://github.com/JetBrains/Exposed) libraries.

How to run the server:
- open the folder `/backend` in IntelliJ IDEA
- build with gradle (this should happen automatically)
- make sure Kotlin plugin is enabled (it is by default)
- open the file located at `src/ro.runtimeterror.cms/Application.kt` and run the main function

How to build the database DDL script:
- follow all the previous steps besides the last one
- configure the database and start the service
- open the file located at `test/database/DDLGenerator.kt` and run the main function