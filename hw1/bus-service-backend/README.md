# Bus Service Backend

## How to run:

```bash
docker compose up -d  # runs MySQL DB
mvn package
cd target
java -jar bus-service-backend-0.0.1-SNAPSHOT.jar
```

## Testing:

```bash
mvn verify # runs all tests
mvn failsafe:integration-test # only integration tests
```

## Functional tests:

- make sure backend is [running](#how-to-run)
- make sure frontend is running (check `README.md` in frontend folder)
- go to the `functional-tests/` folder and run maven tests `mvn verify`
