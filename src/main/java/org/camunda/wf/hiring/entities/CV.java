package org.camunda.wf.hiring.entities;

import java.util.Calendar;

public class CV {
	private String id;
	private String name;
	private String address;
	private String email;
	private String tel;
	private Calendar birthday;
	private String study;
	private String degree;
	private String grade;
	private String sectors;
	private String motivation;
	private String skills;
	private String experience;
	private Boolean isAccepted;
	private String rating;

	public CV() {
		this.name = "Muster";
	}
	
	public CV(String id, String name) {
		this.name = name;
		this.setId(id);
	}
	// Birthday not included yet
	public CV(String id, String name, String address, String email, String tel, String study, String degree, 
			String grade, String sectors, String motivation, String skills, String experience, Boolean isAccepted, String rating) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.study = study;
		this.degree = degree;
		this.grade = grade;
		this.sectors = sectors;
		this.motivation = motivation;
		this.skills = skills;
		this.experience = experience;
		this.isAccepted = isAccepted;
		this.rating = rating;
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSectors() {
		return sectors;
	}

	public void setSectors(String sectors) {
		this.sectors = sectors;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}


/*
class CV {

    private String instanceID;
    private String name;
    private String firstname;
    private String address;
    private String email;
    private Calendar birthday;
    private String study;
    private String degree;
    private String grade;
    private String sectors;
    private String specialSkills;
    private String practicalExperience;
    private int rating;
    private Calendar receivedOn;

    public CV() {
        //the famous empty constructor for Jackson
    }

    public CV(String instanceID,String name, String address,String email,Calendar birthday,String study,String degree,String grade,String sectors,String specialSkills,String practicalExperience,int rating ) {
      
    }

	public String getInstanceID() {
		return instanceID;
	}

	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSectors() {
		return sectors;
	}

	public void setSectors(String sectors) {
		this.sectors = sectors;
	}

	public String getSpecialSkills() {
		return specialSkills;
	}

	public void setSpecialSkills(String specialSkills) {
		this.specialSkills = specialSkills;
	}

	public String getPracticalExperience() {
		return practicalExperience;
	}

	public void setPracticalExperience(String practicalExperience) {
		this.practicalExperience = practicalExperience;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int raing) {
		this.rating = raing;
	}

	public Calendar getReceivedOn() {
		return receivedOn;
	}

	public void setReceivedOn(Calendar receivedOn) {
		this.receivedOn = receivedOn;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name="+getName()+"\n");
		return sb.toString();
	}

}
*/
