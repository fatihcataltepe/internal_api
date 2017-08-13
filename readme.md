# Internal api
This project is implemented to simulate a simple web service enables money transaction between registered users.

## Building and running
1. Checkout project from git
2. Go to project directory: `cd /path/to/project/internal-api`
3. Execute the following command: `sbt universal:packageBin`
4. `internal-api-<version_number>.zip` created in the `target/universal` path. Unzip that `.zip` file
5. Go to `target/universal/internal-api-<version_number>` directory
6. If you are working on MAC OS X execute `chmod +x bin/*`
7. Execute the following command: `bin/internal-api` to run the application with default configurations. Your web service should be up and running

## Configuration
In the `target/universal/internal-api-<version_number>` directory, there is a `conf` folder. You will see
 - `logback.xml` to configure your loggig settings
 - `application.conf` to configure your application settings
 
 Note that with default configurations, the application can already works. However, you can customise if you want.
 
 ### Application.conf
 ```hocon
 internal-api = {
   url = 127.0.0.1
   port = 8080
 }
```
| name | type | notes |
| --- | --- | --- | 
| url | string | the url in which web server will run | 
| port | int | the port in which web server will run | 


 ## Inteface
 Since this is a demo project only 1 interface is implemented which enables money transactions between registered users.
 
 1. **GET** /money-transaction?from=<id_1>&to=<id_2>&amount=<amount>
 This routes takes 3 mandatory parameters. 
 
 | name | type | notes |
 | --- | --- | --- | 
 | from | string | the id of the user who sends the money | 
 | to | int | the id of the user who receives the money | 
 | amount | double | the amount of the money to be transferred|
 
 - The route returns one of the following responses:
 
 1.
 
 ```json
 {
     "status": "Success",
     "message": "Transaction is completed"
 }
 ```  
 
  2.
  
  ```json
  {
      "status": "Error",
      "message": "Current account <currAmount> is not sufficient to transfer <transactionAmount>"
  }
  ```  
  
   3.
   
   ```json
   {
       "status": "Error",
       "message": "Account related to user: <userId> does not exist"
   }
   ```  
 
 ## Notes
 - A fake data store is implemented and basically stores a map of user and balance tuples. In a real life scenario, this can be replaced with a real database.
 - Unit tests can be found under test directory.
 

 
