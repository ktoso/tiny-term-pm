package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.model.Iteration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "iterations")
public class IterationsResponse {

    @XmlElement(name = "iteration")
    public List<Iteration> _iterations;

    public IterationsResponse() {
    }

//    public List<Iteration> getIterations() {
//        return _iterations;
//    }
//
//    public void setIterations(List<Iteration> iterations) {
//        this._iterations = iterations;
//    }
}
