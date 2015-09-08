package jtechlog.cache.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable("employees")
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Cacheable("employee")
    public Employee findOne(long id) {
        return employeeRepository.findOne(id);
    }

    @CachePut(value = "employee", key = "#result.id")
    @CacheEvict(value = "employees", allEntries = true)
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

}
