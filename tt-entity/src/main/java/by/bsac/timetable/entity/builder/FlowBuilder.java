package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Flow;

public class FlowBuilder {

	private short idFlow;
	private String name;

	public FlowBuilder id(short idFlow) {
		this.idFlow = idFlow;
		return this;
	}

	public FlowBuilder name(String name) {
		this.name = name;
		return this;
	}

	public Flow build() {
		Flow flow = new Flow();
		flow.setIdFlow(idFlow);
		flow.setName(name);
		return flow;
	}
}