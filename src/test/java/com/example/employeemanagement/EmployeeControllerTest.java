import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.employeemanagement.controller.EmployeeController;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = new Employee(1L, "John Doe", "1990-01-01", 50000, null, "123 Main St", "Developer", "2020-01-01", 10, null);
        Employee employee2 = new Employee(2L, "Jane Smith", "1992-02-02", 60000, null, "456 Elm St", "Manager", "2019-01-01", 15, null);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee(1L, "John Doe", "1990-01-01", 50000, null, "123 Main St", "Developer", "2020-01-01", 10, null);

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"dateOfBirth\":\"1990-01-01\",\"salary\":50000,\"address\":\"123 Main St\",\"role\":\"Developer\",\"joiningDate\":\"2020-01-01\",\"yearlyBonusPercentage\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1L, "John Doe", "1990-01-01", 50000, null, "123 Main St", "Developer", "2020-01-01", 10, null);

        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"dateOfBirth\":\"1990-01-01\",\"salary\":50000,\"address\":\"123 Main St\",\"role\":\"Developer\",\"joiningDate\":\"2020-01-01\",\"yearlyBonusPercentage\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }
}