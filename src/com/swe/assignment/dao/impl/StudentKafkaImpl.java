package com.swe.assignment.dao.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;

import com.swe.assignment.bean.StudentBean;
import com.swe.assignment.dao.StudentRecord;

public class StudentKafkaImpl {
	// variable to hold the singleton database instance
	private static StudentKafkaImpl instance = null;
	public static final String TOPIC_NAME = "survey-topic";
	public static final String SERVER = "swe645-kafka-cluster-kafka-bootstrap:9092";
	private Producer<Long, StudentRecord> producer;
	private KafkaConsumer<Long, StudentRecord> kafkaConsumer;
	private ConsumerRecords<Long, StudentRecord> records;
	List<String> studList =new ArrayList<String>();
	List<StudentRecord> sb =new ArrayList<StudentRecord>();
	private StudentKafkaImpl() {
		setKafkaProducer();
		setKafkaConsumer();
	}

	/**
	 * creating a singleton instance of database connection class
	 * 
	 * @return
	 */
	public static synchronized StudentKafkaImpl getInstance() {
		// if singleton instance is not available create an instance object
		if (null == instance) {
			instance = new StudentKafkaImpl();
		}
		// else return the existing instance object
		return instance;
	}

	private void setKafkaProducer() {
		Properties producerProperties = new Properties();
		producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentRecord.class.getName());
		producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
		producer = new KafkaProducer<>(producerProperties);
	}

	private void setKafkaConsumer() {
		Properties consumerProperties = new Properties();
		consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StudentRecord.class.getName());
		consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
		
	}
	

	private Producer<Long, StudentRecord> getKafkaProducer() {
		return producer;
	}

	public StudentBean readStudent(int id) throws Exception {
		//if(null!=records) {
			//for (ConsumerRecord<Long, StudentRecord> record : records) {
				//System.out.println("Received: " + record.key() + ":" + record.value());
				//StudentRecord temp = (StudentRecord) record.value();
		for(StudentRecord s:sb){
				if (id == s.getId()) {
					return s.convert();
				}
			//}
		//}
		}
		return null;
	}

	public List<String> readStudentIds() throws Exception {
		List<String> studIDList = new ArrayList<String>();
		if(!sb.isEmpty()){
		sb.clear();
		}
		kafkaConsumer.poll(0);
		// Now there is heartbeat and consumer is "alive"
		kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
		// Now consume
		records =  kafkaConsumer.poll(Duration.ofMillis(100));
		
		System.out.println("Fetched " + records.count() + " records");
		for (ConsumerRecord<Long, StudentRecord> record : records) {
			System.out.println("Received: " + record.key() + ":" + record.value());
			StudentRecord temp = (StudentRecord) record.value();
			sb.add(temp);
			studIDList.add(String.valueOf(temp.getId()));
			

		}
		for(String s: studIDList){
			if(!studList.contains(s)){
			studList.add(s);	
			}
			
		}

		//kafkaConsumer.commitSync();
		return studList;
	}

	public void saveToDatabase(StudentBean studentBean) throws Exception {
		Producer<Long, StudentRecord> producer = getKafkaProducer();
		StudentRecord stdRecord = new StudentRecord(studentBean);
		ProducerRecord<Long, StudentRecord> record = new ProducerRecord<Long, StudentRecord>(TOPIC_NAME, null,
				Long.valueOf(stdRecord.getId()), stdRecord);
		producer.send(record);
		System.out.println("Send record#" + stdRecord);
	}

}
