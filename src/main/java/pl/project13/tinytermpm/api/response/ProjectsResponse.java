package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.tinypm.model.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
public class ProjectsResponse {

    @XmlElement(name = "project")
    List<Project> _project;

    public ProjectsResponse() {
    }

    public List<Project> getProjects() {
        return _project;
    }

    public void setProjects(List<Project> projects) {
        this._project = projects;
    }
}
