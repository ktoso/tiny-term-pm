package pl.project13.tinytermpm.api.harvest.model

import org.joda.time.DateTime
import javax.xml.bind.annotation.{XmlElementWrapper, XmlRootElement, XmlElement}
import java.util.{ArrayList, Collections}


/**
 * <daily>
 *  <for_day type="date">Wed, 18 Oct 2006</for_day>
 *  <day_entries>
 *    <day_entry>
 *      <id type="integer">195168</id>
 *      <spent_at type="date">2006-10-18</spent_at>
 *      <user_id type="integer">218356</user_id>
 *      <client>Iridesco</client>
 *      <project_id>1234567</project_id>
 *      <project>Harvest</project>
 *      <task_id>126789</task_id>
 *      <task>Backend Programming</task>
 *      <!-- Includes running timer if any -->
 *      <hours type="float">2.06</hours>
 *      <notes>Test api support</notes>
 *      <!-- OPTIONAL returned only if a timer is running -->
 *      <timer_started_at type="datetime">
 *        Wed, 18 Oct 2006 09:53:06 -0000
 *      </timer_started_at>
 *      <created_at type="datetime">Thu, 17 Nov 2011 10:19:57 +0000</created_at>
 *      <updated_at type="datetime">Thu, 17 Nov 2011 12:24:03 +0000</updated_at>
 *      <!-- OPTIONAL if account has timestamps enabled -->
 *      <started_at>8:00am</started_at>
 *      <ended_at>10:00am</ended_at>
 *    </day_entry>
 *    <day_entry>
 *      ...
 *    </day_entry>
 *  </day_entries>
 *  <!-- These are the project-task combinations that can be added to
 *  the timesheet. Not present in readonly timesheets or for users
 *  without assigned projects. -->
 *  <projects>
 *    <project>
 *      <name>Click and Type</name>
 *      <code></code>
 *      <id type="integer">3</id>
 *      <client>AFS</client>
 *      <tasks>
 *        <task>
 *          <name>Security support</name>
 *          <id type="integer">14</id>
 *          <billable type="boolean">true</billable>
 *        </task>
 *        <task>
 *          ...
 *        </task>
 *      </tasks>
 *    </project>
 *    <project>
 *      ...
 *    </project>
 *  </projects>
 * </daily>
 */
@XmlRootElement(name = "daily")
class Daily {

  
  @XmlElement(name = "for_day")
  var forDay: DateTime = _

  @XmlElementWrapper(name = "day_entries")
  @XmlElement(name = "day_entry")
  var dayEntries: java.util.List[DayEntry] = new ArrayList[DayEntry]
}
