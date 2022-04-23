package com.pixels.photograd.core.schedulers;

import com.pixels.photograd.core.configs.PageUpdateSchedulerConfiguration;
import com.pixels.photograd.core.services.PageUpdateService;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = PageUpdateSchedulerConfiguration.class)
public class PageUpdateScheduler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(PageUpdateScheduler.class);

    private int schedulerId;

    @Reference
    private Scheduler scheduler;
    
    @Reference
    public PageUpdateService pageService;

    @Activate
    protected void activate(PageUpdateSchedulerConfiguration config) {
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(PageUpdateSchedulerConfiguration config) {
        removeScheduler();
    }

    private void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    private void addScheduler(PageUpdateSchedulerConfiguration config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(schedulerId));
        scheduleOptions.canRunConcurrently(true);
        scheduler.schedule(this, scheduleOptions);
    }
   @Override
    public void run() {
       LOG.info("\n ====> RUN METHOD  ");
       pageService.updatePageData();
    }
}