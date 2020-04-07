# Assignment 9

### Design Patterns used for the todo tracking system:
* Builder Pattern
>```Option```, ```ToDoItem```: these two class have multiple fields, many of which are optional parameters.
The builder class builds the complex object using simple objects step by step, more flexible construction.

* Factory Pattern
>```RequestFactory```, ```AddRequest```,```CompleteRequest```, ```DisplayRequest```: create the concrete request implementation
>without exposing the creation logic to the client. Refer the newly created request object using the common ```IRequest``` interface.
>Would be much easier to add more request subtypes for extension.
>

* Singleton Pattern
>```CSVParser```: the csv parser is a tool class, it provides reading and writing methods for all csv files. One instance of the CSVParser 
>class is created in the complete execution of the program.  
