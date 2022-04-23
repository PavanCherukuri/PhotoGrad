package com.pixels.photograd.core.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "PhotoGrad - Scheduler Configuration",description = "Sling scheduler configuration")
public @interface PageUpdateSchedulerConfiguration {

	@AttributeDefinition(
            name = "Scheduler name",
            description = "Name of the scheduler",
            type = AttributeType.STRING)
    public String schedulerName() default "Photo Grad Scheduler dialy";

    @AttributeDefinition(
            name = "Cron Expression",
            description = "Cron expression used by the scheduler",
            type = AttributeType.STRING)
    public String cronExpression() default "0 0 0 1/1 * ? *"; 
}
