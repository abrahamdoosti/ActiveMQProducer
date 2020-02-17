package com.activemq.sender.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//if we don't annotate the getters only then JaxB will think we have two ways to get the attribute, 
//one through getter and another through the field itself.
//To fix that issue we tell JAXB that we intend to use the XmlAccessType.FIELD 
@XmlAccessorType(XmlAccessType.FIELD) // This line was added
public class Students {
	
	@XmlElementWrapper
	@XmlElement(name="Student")
	private List<Student> studentList=new ArrayList<>();
	
	private String studentGroupName;

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
	
	public String getStudentGroupName() {
		return studentGroupName;
	}

	public void setStudentGroupName(String studentGroupName) {
		this.studentGroupName = studentGroupName;
	}

	public void addStudent(Student student) {
		this.getStudentList().add(student);
	}
	

}
