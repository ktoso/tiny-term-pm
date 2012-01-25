package pl.project13.tinytermpm.api.response;

import pl.project13.tinytermpm.api.model.UserStory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "userStories")
public class UserStoriesResponse {

    @XmlElement(name = "userStory")
    List<UserStory> _stories;

    public UserStoriesResponse() {
    }

    public List<UserStory> getUserStories() {
        return _stories;
    }

    public void setUserStories(List<UserStory> projects) {
        this._stories = projects;
    }
}
