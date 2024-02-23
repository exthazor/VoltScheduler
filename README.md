# Getting Started with the Project

This guide will walk you through the process of setting up your development environment, cloning the project from GitHub, configuring an H2 database, and running the application. Please follow each step carefully to ensure a smooth setup and execution of the project.

## Step 1: Install Java Development Kit (JDK)

- Download the JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
- Install the JDK on your system following the installer's instructions.
- Set up the `JAVA_HOME` environment variable:
  - **Windows**: Set `JAVA_HOME` to your JDK installation path via System Properties -> Environment Variables.
  - **macOS/Linux**: Add `export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))` to your `.bashrc`, `.bash_profile`, or `.zshrc` file.

## Step 2: Install IntelliJ IDEA

- Download IntelliJ IDEA Community Edition from the [JetBrains website](https://www.jetbrains.com/idea/download/).
- Install IntelliJ IDEA by following the installation wizard.
- Launch IntelliJ IDEA after installation.

## Step 3: Clone the Project from GitHub

- Open IntelliJ IDEA and select `File -> New -> Project from Version Control`.
- In the URL field, enter the GitHub repository URL of the project.
- Choose a directory on your local machine where the project should be saved.
- Click `Clone`.

## Step 4: Create an H2 Database

- Create a new file at the directory root and name it `test.mv.db`.
- Save it.

## Step 5: Configure H2 Database

- Download the H2 Database from the [H2 website](https://www.h2database.com/).
- Install and run H2 Database.
- Configure a new database with the following settings (adjust according to your project needs):
  - **JDBC URL**: `jdbc:h2:file:<location_of_your_project>/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
  - **Username**: sa
  - **Password**: (leave blank or set a password)
- Remember these credentials as they will be needed to configure the database connection in your project.

## Step 6: Configure the Application

- Open the project in IntelliJ IDEA.
- Locate the `application.properties` file in the `src/main/resources` directory.
- Update the database connection properties with the credentials you set up for the H2 Database.
  - Example:
    ```
    spring.datasource.url=jdbc:h2:~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.username=sa
    spring.datasource.password=
    spring.datasource.driverClassName=org.h2.Driver
    ```

## Step 7: Run the Application

- In IntelliJ IDEA, locate the main application file (usually found in `src/main/java` under a directory matching your group or package name).
- Right-click the file and select `Run 'Application'`.
- The application should now start, and you will be able to interact with it through Postman.

## Step 8: Run the Database

- Go to [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/).
- Copy all the content from `schema.sql` file under `resources` folder.
- In h2-console, add the location of `test.mv.db` and click on connect.
- After being connected, paste the content from `schema.sql` on the white box and click on run button.

## Step 9: Use the Application

For usage information please refer to [GUIDE.md](GUIDE.md).

## Troubleshooting

If you encounter any issues during setup or execution, please check the following:

- Ensure that JDK is correctly installed and `JAVA_HOME` is properly set.
- Verify the H2 Database is running and accessible.
- Confirm that all project dependencies are correctly resolved by IntelliJ IDEA (look for any error indicators in the `pom.xml`).
