package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.tinypm.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
public class TasksResponse {

    @XmlElement(name = "task")
    List<Task> _tasks;

    public TasksResponse() {
    }

    public List<Task> getTasks() {
        return _tasks;
    }

    public void setTasks(List<Task> users) {
        this._tasks = users;
    }
}
