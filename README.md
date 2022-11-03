# Account Service
Simple REST API app to store employees payrolls.

## Technologies
- Java
- Spring Boot among with security component (authorization and authentication)
- JSON as data transfer
- H2 database

## Endpoints

#### Authentication

- *POST* **api/auth/signup** <br>
allows the user to register on the service
```json
{
   "name": "<String value, not empty>",
   "lastname": "<String value, not empty>",
   "email": "<String value, not empty>",
   "password": "<String value, not empty>"
}
```

- *POST* **api/auth/changepass** <br>
changes a user password
```json
{
   "new_password": "<String value, not empty>"
}
```

#### Business

- *GET* **api/empl/payment** <br>
gives access to the employees payrolls. It takes the `period` parameter that specifies the period (the month and year) and provides the information for this period. If the parameter period is not specified, the endpoint provides information about the employee's salary for each period

- *POST* **api/acct/payments** <br>
uploads payrolls
```json
[
    {
        "employee": "<user email>",
        "period": "<mm-YYYY>",
        "salary": 12000
    },
    {
        "employee": "<user1 email>",
        "period": "<mm-YYYY>",
        "salary": 15000
    },
    {
        "employee": "<userN email>",
        "period": "<mm-YYYY>",
        "salary": 14000
    }
]
```

- *PUT* **api/acct/payments** <br>
changes the salary of a specific user
```json
{
    "employee": "<user email>",
    "period": "<mm-YYYY>",
    "salary": 12000
}
```

#### Service

- *GET* **api/security/events** <br>
returns an array of objects representing security events

- *GET* **api/admin/user** <br>
displays information about all users

- *PUT* **api/admin/user/role** <br>
changes user roles
```json
{
   "user": "<String value, not empty>",
   "role": "<User role>",
   "operation": "<[GRANT, REMOVE]>"
}
```

- *PUT* **api/admin/user/access** <br>
locks/unlocks users
```json
{
   "user": "<String value, not empty>",
   "operation": "<[LOCK, UNLOCK]>" 
}
```

- *DELETE* **api/admin/user** <br>
deletes a user
```json
{
   "user": "<user email>",
   "status": "Deleted successfully!"
}
```
