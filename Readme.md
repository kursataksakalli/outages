###KRAKENFLEX OUTAGES BACKEND
Program main logic
1. Retrieves all outages from the `GET /outages` endpoint
2. Retrieves information from the `GET /site-info/{siteId}` endpoint for the site with the ID `norwich-pear-tree`
3. Filters out any outages that began before `2022-01-01T00:00:00.000Z` or don't have an ID that is in the list of
   devices in the site information
4. For the remaining outages, it should attach the display name of the device in the site information to each appropriate outage
5. Sends this list of outages to `POST /site-outages/{siteId}` for the site with the ID `norwich-pear-tree`

##How to Install, Run and Test the Project
###Build Project
This application is developed with Java 11. Some technologies in the stacks are:
Spring Boot, JUnit5 with Mockito, Swagger with SpringBoot.<br />

To build application;<br />
1-) Install maven 3.5 or later, Java JDK 11<br />
2-) Please make files src/main/docker/dockerize.ps1, src/main/docker/run-docker.ps1, 
src/main/docker/dockerize.sh, src/main/docker/run-docker.sh executable.<br />
3-) If you want to configure application you can change environment variables at src/main/docker/docker-compose.yml <br />
4-) At project root directory run command "mvn clean package"<br />

###Run Project
To run application<br />
1-) Go to folder src/main/docker/ <br />
2-) Run dockerize.ps1(Windows) or dockerize.sh(Linux) <br />
3-) At port 8080 application will be started. <br />
4-) To execute main logic send post request to localhost:8080/main <br />
NOTE 1: You can go to swagger page http://localhost:8080/swagger-ui/index.html <br />
NOTE 2: You can change environment variables at src/main/docker/docker-compose.yml to configure application.<br />

###Test Project
To test application<br />
1-) If you want to make integration tests, please activate environment variable DO_INTEGRATION_TESTS at src/main/docker/docker-compose.yml or src/main/resources/application.yml (Unit tests always run)<br />
2-) At project root folder run "mvn test"<br />

####ENVIRONMENT VARIABLES
      - PORT=8080 ==> Application port
      - CONTROL_DATE=2022-01-01T00:00:00.000Z ==> Filter date
      - DO_INTEGRATION_TESTS=false ==> Do integration tests
      - BASE_URL=https://api.krakenflex.systems/interview-tests-mock-api/v1/ ==> Base server url
      - API_KEY=**** ==> Api key

