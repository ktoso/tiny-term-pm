package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.model.Iteration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "iterations")
public class IterationsResponse {

    @XmlElement(name = "iteration")
    List<Iteration> _project;

    public IterationsResponse() {
    }

    public List<Iteration> getIterations() {
        return _project;
    }

    public void setIterations(List<Iteration> projects) {
        this._project = projects;
    }
}
