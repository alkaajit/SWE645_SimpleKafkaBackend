package com.swe.assignment.dao.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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
	public static final String TOPIC_NAME = "topic-1";
	public static final String SERVER = "localhost:9092";
	private Producer<Long, StudentRecord> producer;
	private KafkaConsumer<Long, StudentRecord> kafkaConsumer;

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
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-group");
		consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StudentRecord.class.getName());
		consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
	}

	private KafkaConsumer<Long, StudentRecord> getKafkaConsumer() {
		return kafkaConsumer;
	}

	private Producer<Long, StudentRecord> getKafkaProducer() {
		return producer;
	}

	public StudentBean readStudent(int id) throws Exception {
		ConsumerRecords<Long, StudentRecord> records = getKafkaConsumer().poll(Duration.ofMillis(3000));
		System.out.println("Fetched " + records.count() + " records");
		for (ConsumerRecord<Long, StudentRecord> record : records) {
			System.out.println("Received: " + record.key() + ":" + record.value());
			StudentRecord temp = (StudentRecord) record.value();
			if (id == temp.getId()) {
				return temp.convert();
			}
		}
		return null;
	}

	public List<String> readStudentIds() throws Exception {
		List<String> studIDList = new ArrayList<String>();
		ConsumerRecords<Long, StudentRecord> records = getKafkaConsumer().poll(Duration.ofMillis(3000));
		System.out.println("Fetched " + records.count() + " records");
		for (ConsumerRecord<Long, StudentRecord> record : records) {
			System.out.println("Received: " + record.key() + ":" + record.value());
			StudentRecord temp = (StudentRecord) record.value();
			studIDList.add(String.valueOf(temp.getId()));

		}

		kafkaConsumer.commitSync();
		return studIDList;
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
