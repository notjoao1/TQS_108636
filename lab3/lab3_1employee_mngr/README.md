# Lab 3.1

## A) AssertJ Method chaining examples

- This example is in method `givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()`. It checks that after adding 3 employees to a mock persistence DB, the `findAll()` method returns an `ArrayList` with size 3, and after extracting the names from each of the `Employee` objects from the `ArrayList`, and checks that the values are exclusively `alex.getName()`, `ron.getName()` and `bob.getName()`.




```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

- This example asserts that the database query returns null.

```java
assertThat(fromDb).isNull();
```

- In this example, we assert that the response from the DB is not `null`, and after that, asserts that the returned email is equal to the previously created email.

```java
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
```


## B) Repository Mock Example

- In this example, we use the `@Mock` annotation to create a mock `EmployeeRepository`, and avoid using the DB. We then inject this mock dependency into `EmployeeServiceImpl`, so that it can be used.


```java
@Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```



## C) @Mock vs @MockBean

- `@Mock` creates a mock, that must be manually injected into other classes that require it.
- `@MockBean` creates a mock bean. This bean is automatically managed by the Spring IoC container, which means we don't need to manually inject it into whatever classes we wanna use it in.


## D) Role of `application-integrationtest.properties`

- `application-integrationtest.properties` will be used when we run integration tests. It's useful since we can specify a different database to run tests on, for example. In this case, we even add `spring.jpa.hibernate.ddl-auto=create-drop`, which automatically creates and drops the database each time integration tests are run. 

To use this file, we must specify it in a test class, like this:

```java
@TestPropertySource(locations = "application-integrationtest.properties")
class D_EmployeeRestControllerIT {

```

## E) Differences between C, D, E

- In C, we are creating a simplified web environment by annotating with `@WebMvcTest`. In this environment. We then use a `MockMvc` object that gets injected with this simplified testing environment.
- In D, we create a test environment with `@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = EmployeeMngrApplication.class)` that doesn't contain an API client.  
- In E, we load the full web context, by using `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`. 
- While D and E might seem similar, in E, we use a full API client. Unmarshalling and marshalling occur.

