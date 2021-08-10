package model;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name, surname, birth, email, phoneNumber, password, registration, role;
	private int accessNumber;
	
	public UserBean() {
		name = "";
		surname = "";
		birth = "";
		email = "";
		phoneNumber = "";
		password = "";
		registration = "";
		role = "";
		accessNumber = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public int getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(int accessNumber) {
		this.accessNumber = accessNumber;
	}
	
	public boolean isAdmin() {
		return role.equals("admin");
	}
	
	public boolean isUser() {
		return (role.equals("utente"));
	}
	
	@Override 
	public boolean equals(Object other) {
		return (this.getEmail().equals(((UserBean)other).getEmail()));
	}

	@Override
	public String toString() {
		return "nome= "+name+" cognome= "+surname+" data nascita= "+birth+" email= "+email+
			   " telefono= "+phoneNumber+" accessi= "+accessNumber;
	}
}
