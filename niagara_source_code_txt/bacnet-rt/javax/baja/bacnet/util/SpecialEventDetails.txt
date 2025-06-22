/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import java.util.List;
import javax.baja.schedule.BAbstractSchedule;
import javax.baja.schedule.BTimeSchedule;

public class SpecialEventDetails
{

  public SpecialEventDetails(BAbstractSchedule daysSchedule, List<BTimeSchedule> timeSchedules)
  {
    this.daysSchedule = daysSchedule;
    this.timeSchedules = timeSchedules;
  }

  public BAbstractSchedule getDaysSchedule()
  {
    return daysSchedule;
  }

  public List<BTimeSchedule> getTimeSchedules()
  {
    return timeSchedules;
  }

  private BAbstractSchedule daysSchedule;

  private List<BTimeSchedule> timeSchedules;
}
