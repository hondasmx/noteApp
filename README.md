# noteApp

- Install postgresql
- Install Maven
- Install Node + npm via maven `mvn frontend:install-node-and-npm`
- Configure credentials in `\src\main\resources\application.properties`
- Configure frontend url (if 3000 port is busy) in `com.artyg.noteapp.config.WebConfig.FRONTEND_URL`
- Run sql script to create the table and populate with the data `\src\main\java\com\artyg\noteapp\sql\init.sql`

Run backend `mvn exec:java`

Run frontend `mvn frontend:npm`