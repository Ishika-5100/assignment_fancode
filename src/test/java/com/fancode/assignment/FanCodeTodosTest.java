package com.fancode.assignment;

import com.fancode.assignment.ApiClient;
import com.fancode.assignment.models.Todo;
import com.fancode.assignment.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

public class FanCodeTodosTest {

    @Test
    public void testFanCodeUsersTodosCompleted() throws IOException {
        ApiClient apiClient = new ApiClient();
        List<User> users = apiClient.getUsers();

        for (User user : users) {
            if (isFanCodeUser(user)) {
                double completedPercentage = getCompletedPercentage(user.getId());
                Assert.assertTrue(completedPercentage > 50,
                        String.format("User %s from FanCode city has completed only %.2f%% of todos.",
                                user.getUsername(), completedPercentage));
            }
        }
    }

    private boolean isFanCodeUser(User user) {
        double lat = user.getAddress().getGeo().getLat();
        double lng = user.getAddress().getGeo().getLng();
        return (lat > -40 && lat < 5 && lng > 5 && lng < 100);
    }

    private double getCompletedPercentage(int userId) throws IOException {
        String todosUri = String.format("todos?userId=%d", userId);
        ApiClient apiClient = new ApiClient();
        List<Todo> todos = apiClient.getTodos(todosUri);

        if (!todos.isEmpty()) {
            long totalTodos = todos.size();
            long completedTodos = todos.stream().filter(Todo::isCompleted).count();
            return (completedTodos * 100.0) / totalTodos;
        }

        return 0.0;
    }
}