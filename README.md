
Log Event Assessment
==============================================================

* **Prerequisites:**
    * Install the latest version of [Java](https://java.com) and [Maven](https://maven.apache.org/download.html).
    * You may need to set your `JAVA_HOME`.



* **Setup:**

* You must also be able to work with [GitHub](https://help.github.com/articles/set-up-git) repositories.
* Clone repository.

        git clone https://github.com/mevipin-kp/LogEventAssessment.git

* **Command-line Instructions:**


Step 1: Build the project

```bash
cd LogEventAssessment
# Compile and run
mvn compile install

```

Step 2 : Run the project
```bash
java -jar target/csuisse-project-1.0-SNAPSHOT-jar-with-dependencies.jar "your-log-file-absolute-path"

```