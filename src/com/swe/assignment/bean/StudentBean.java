package com.swe.assignment.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * To store all the student survey form data
 * 
 * @author ambily
 *
 */
@XmlRootElement
public class StudentBean {
	@XmlElement(name = "studentId")
	String id;
	@XmlElement(name = "username")
	String userName;
	@XmlElement(name = "streetaddress")
	String streetAddress;
	String city;
	String state;
	int zip;
	@XmlElement(name = "tel")
	String telephone;
	String email;
	String url;
	@XmlElement(name = "dos")
	String dateOfSurvey;
	String[] likedMost;
	@XmlElement(name = "Get_interested")
	String getInterested;
	@XmlElement(name = "gradmonth")
	String gradMonth;
	@XmlElement(name = "gradyear")
	String gradYear;
	@XmlElement(name = "Comments")
	String comments;
	@XmlElement(name = "Recommendation")
	String recommendation;

	public String getGradMonth() {
		return gradMonth;
	}

	public void setGradMonth(String gradMonth) {
		this.gradMonth = gradMonth;
	}

	public String getGradYear() {
		return gradYear;
	}

	public void setGradYear(String gradYear) {
		this.gradYear = gradYear;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getGetInterested() {
		return getInterested;
	}

	public void setGetInterested(String getInterested) {
		this.getInterested = getInterested;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDateOfSurvey() {
		return dateOfSurvey;
	}

	public void setDateOfSurvey(String dateOfSurvey) {
		this.dateOfSurvey = dateOfSurvey;
	}

	public StudentBean() {
	}

	public String[] getLikedMost() {
		return likedMost;
	}

	public void setLikedMost(String[] likedMost) {
		this.likedMost = likedMost;
	}

}
