package jtechlog.cache.ui;

import jtechlog.cache.backend.Employee;
import jtechlog.cache.backend.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // curl -H "Content-Type: application/json"  http://localhost:8080/api/employees
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity
                .ok()
                .eTag(hashToEtag(employees.hashCode()))
                .body(employees);
    }

    // curl -H "Content-Type: application/json"  http://localhost:8080/api/employees/1
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<Employee> findOne(@PathVariable("id") long id) {
        Employee employee = employeeService.findOne(id);
        return ResponseEntity
                .ok()
                .eTag(hashToEtag(employee))
                .body(employee);
    }

     // curl -H "Content-Type: application/jse":"xyz"}' http://localhost:8080/api/employees
    @RequestMapping(value = "/employees", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    private String hashToEtag(Object o) {
        return "\"" + Integer.toString(o.hashCode()) + "\"";
    }
}
