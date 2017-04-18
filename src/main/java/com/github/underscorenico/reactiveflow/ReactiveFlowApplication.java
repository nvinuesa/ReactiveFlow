package com.github.underscorenico.reactiveflow;

import com.github.underscorenico.reactiveflow.annotation.ReactiveFlow;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ReactiveFlowApplication implements InitializingBean, ApplicationContextAware, ApplicationListener<ApplicationContextEvent> {

	private ApplicationContext applicationContext;
	private String[] processes;

	@Autowired
	private RepositoryService repositoryService;

	public ReactiveFlowApplication() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			// deploy the process application
			start();
		}
	}

	public void afterPropertiesSet() {
		final Map<String, Object> beans = Optional
			.ofNullable(applicationContext.getBeansWithAnnotation(ReactiveFlow.class))
			.orElse(Collections.EMPTY_MAP);

		if (beans.size() > 1) {
			throw new IllegalStateException("requires exactly one bean to be annotated with @ReactiveFlow, found: " + beans.keySet());
		} else if (beans.size() == 1) {

			this.processes = (String[]) AnnotationUtils
				.getAnnotationAttributes(AnnotationUtils
					.findAnnotation(beans.entrySet().iterator().next().getValue().getClass(), ReactiveFlow.class))
				.get("value");

			this.start();
		}
	}

	private void start() {

		repositoryService.createProcessDefinitionQuery().latestVersion().list();
		System.out.println("rf start");
	}
}
