package com.quickdone.znwh.pojo;

import java.io.Serializable;

/**
 * 流程配置的节点
 */
public class ProcessNode implements Serializable {

	private static final long serialVersionUID = -447348948755578199L;
	private String id;
	private String sourceRef;
	private String targetRef;
	private String defaultRef;
	private String exceptionRef;
	private String answer;//标准的语义结果
	private String content;

	private String start;//值为1   标识是第一个节点

	private String label;//标签

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}

	public String getDefaultRef() {
		return defaultRef;
	}

	public void setDefaultRef(String defaultRef) {
		this.defaultRef = defaultRef;
	}

	public String getExceptionRef() {
		return exceptionRef;
	}

	public void setExceptionRef(String exceptionRef) {
		this.exceptionRef = exceptionRef;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
