package pl.project13.tinytermpm.model

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class UserStoryTest extends FlatSpec with ShouldMatchers {

  val userStoryXml =
    <userStory>
     <id>2</id>
     <name>As an editor I want to be able to browse drafts.</name>
     <iterationId>1</iterationId>
     <position>1</position>
     <tags>
       <tag>drafts</tag>
       <tag>editors</tag>
     </tags>
   </userStory>

  val userStoriesXml =
    <userStories>
      <userStory>
        <id>1</id>
        <name>As an editor I want to be able to publish a new topic.</name>
        <iterationId>1</iterationId>
        <position>0</position>
        <tags>
          <tag>editors</tag>
          <tag>publishing</tag>
        </tags>
      </userStory>
      <userStory>
        <id>2</id>
        <name>As an editor I want to be able to browse drafts.</name>
        <iterationId>1</iterationId>
        <position>1</position>
        <tags>
          <tag>drafts</tag>
          <tag>editors</tag>
        </tags>
      </userStory>
    </userStories>

  "UserStory" should "be deserialized from xml" in {
    val story = UserStory(userStoryXml)

    story should have (
      'id (2),
      'position (1),
      'name ("As an editor I want to be able to browse drafts."),
      'iterationId (1)
    )
  }

  "UserStories" should "be deserialized from xml" in {
    val stories = UserStories(userStoriesXml)

    stories should have length (2)
  }
}