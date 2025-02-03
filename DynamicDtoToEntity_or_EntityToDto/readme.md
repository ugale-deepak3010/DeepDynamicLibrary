### DtoEntityConverter 
### How to convert Dto to Entity or Entity to Dto fully dynamically for any class or model

Refer the attached java file.

Prerequisites

- [ ] Dependency must be added in Pom.xml file:
`<dependency>`
`		  <groupId>org.modelmapper</groupId>`
`		  <artifactId>modelmapper</artifactId>`
`		  <version>3.0.0</version>`
`</dependency>`
![image](https://github.com/user-attachments/assets/bb8d1cb6-b11a-4ef2-b3ac-feca35b31a54)

- [ ] Hierarchy must be like:

- com.example.Dto
- com.example.Model
- com.example.Controller..
- com.example.service......

- [ ] Which field we need to put in Dto or Entity so field name must be match!
Like:

- `firstName` for `firstName`
- **Note: Extra additional field will accept but never map**

- [ ] class name must be standard like:

- `UserDto` for `User`
- `EmployeeDto` for `Employee`
- `Student` for `StudentDto`

- [ ]  **Happy coding!**
