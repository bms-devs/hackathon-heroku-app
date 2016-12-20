# WC Monitor [hackathon-heroku-app]

This is an application for monitoring the availability of toilets.

## Running the application

Using maven: `mvn spring-boot:run`.
After starting the application, point your browser to http://localhost:3332.

## Using a Postgres database for persistence

For running the application using a real [Postgres](http://www.postgresql.org/) database, uncomment all property
definitions in the `application.properties` file and put the configuration for your Postgres instance there.
You can also use [Docker](http://docker.com) for starting a Postgres database. Just run the `docker-postgres.sh` script
and it will create a Postgres container for you. When using docker, all you have to change in the
`application.properties` file is `spring.datasource.url`. For boot2docker users, the host name should already be
correct. For Linux users it has to be changed to localhost.

## License

Code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0.txt).
