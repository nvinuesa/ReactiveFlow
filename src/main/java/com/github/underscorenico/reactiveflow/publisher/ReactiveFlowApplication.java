package com.github.underscorenico.reactiveflow.publisher;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.camunda.bpm.extension.reactor.spring.listener.ReactorTaskListener;
import org.springframework.stereotype.Component;

@Slf4j
public class ReactiveFlowApplication {

	@Component
	@CamundaSelector
	@SuppressWarnings("unused")
	public static class MyTaskCreateListener extends ReactorTaskListener {
		@Override
		public void notify(DelegateTask delegateTask) {
			System.out.println("listener");
		}
	}
}
