package gb.example.task_manager;

import gb.example.task_manager.model.Task;
import gb.example.task_manager.repository.TaskRepository;
import gb.example.task_manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {
        List<Task> tasks = List.of(
                new Task(1L, "Task 1", false),
                new Task(2L, "Task 2", true)
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testAddTask() {
        Task newTask = new Task(null, "New Task", false);
        Task savedTask = new Task(1L, "New Task", false);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.addTask("New Task");

        assertNotNull(result.getId());
        assertEquals("New Task", result.getName());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testMarkAsCompleted() {
        Task task = new Task(1L, "Task 1", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.markAsCompleted(1L);

        assertTrue(task.isCompleted());
        verify(taskRepository, times(1)).save(task);
    }
}
