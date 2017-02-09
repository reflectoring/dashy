package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** registers the UrlSourceJob at the SchedulingService */
@Component
public class UrlSourceJobRegistrar {

  private SchedulingService schedulingService;

  /**
   * using the scheduling service to register the {@link UrlSourceJob} at startup
   *
   * @param schedulingService injecting the scheduling service to register job class
   */
  @Autowired
  public UrlSourceJobRegistrar(SchedulingService schedulingService) {
    this.schedulingService = schedulingService;
    this.schedulingService.registerJob(UrlSourceJob.JOB_TYPE, UrlSourceJob.class);
  }
}
