import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.employeemanagement.controller.DepartmentController;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.service.DepartmentService;
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

public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Department department1 = new Department(1L, "HR", null, null);
        Department department2 = new Department(2L, "IT", null, null);
        List<Department> departments = Arrays.asList(department1, department2);

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("IT"));
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = new Department(null, "Finance", null, null);
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Finance\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        Department department = new Department(1L, "Finance", null, null);
        when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(department);

        mockMvc.perform(put("/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Finance\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/departments/1"))
                .andExpect(status().isNoContent());
    }
}