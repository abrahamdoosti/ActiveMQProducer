package com.activemq.sender;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import com.activemq.sender.model.Student;
import com.activemq.sender.model.Students;
 
public class MessageSender {
     
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = "tcp://ec2-18-216-2-76.us-east-2.compute.amazonaws.com:61616";
     
    // default broker URL is : tcp://localhost:61616"
    private static String subject = "JCG_QUEUE"; // Queue Name.You can create any/many queue names as per your requirement. 
     
    public static void main(String[] args) throws JMSException { 
		// used to configure log4j
		BasicConfigurator.configure();

		// Getting JMS connection from the server and starting it
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Creating a non transactional session to send/receive JMS message.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Destination represents here our queue 'JCG_QUEUE' on the JMS server.
		// The queue will be created automatically on the server.
		Destination destination = session.createQueue(subject);

		// MessageProducer is used for sending messages to the queue.
		MessageProducer producer = session.createProducer(destination);
		
		Students students=new Students();
		students.setStudentGroupName("AWS Dev Group");
		students.addStudent(new Student(123,"testFirst","testLast"));
		students.addStudent(new Student(124,"test2First","test2Last"));		
		JAXBContext context=null;
		String marshalledString="";
		try {
			 context=JAXBContext.newInstance(Students.class);
			 Marshaller studentsMarshaller=context.createMarshaller();
			 studentsMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 StringWriter stringWriter=new StringWriter(); 
			 studentsMarshaller.marshal(students, stringWriter);
			 marshalledString=stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Sending Marshalled Student...");
		
		producer.send(session.createTextMessage(marshalledString));
		
		
	/*	for (int i = 0; i < 10; i++) {
			// We will send a small text message saying 'Hello World!!!'
			System.out.println("Sending Message...");
			TextMessage message = session.createTextMessage("Hello !!! Welcome to the world of ActiveMQ. " + i);

			// Here we are sending our message!
			producer.send(message);

			System.out.println("JCG printing@@ '" + message.getText() + "'");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		connection.close();

	}
}
