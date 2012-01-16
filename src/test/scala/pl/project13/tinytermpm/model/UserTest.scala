package pl.project13.tinytermpm.model

import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class UserTest extends FlatSpec with ShouldMatchers {

  val userXml =
    <user>
      <active>true</active>
      <id>1</id>
      <name>Alice</name>
    </user>

  val usersXml =
    <users>
      <user>
        <active>true</active>
        <id>1</id>
        <name>Alice</name>
      </user>
      <user>
        <active>false</active>
        <id>2</id>
        <name>Bob</name>
      </user>
    </users>

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

  "User" should "be deserialized from xml" in {
    val user = User(userXml)
    
    user should have (
      'active (true),
      'id (1),
      'name ("Alice")
    )
  }
  
  "Users" should "be deserialized from xml" in {
    val users = Users(usersXml)

    users should have length (2)
  }

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