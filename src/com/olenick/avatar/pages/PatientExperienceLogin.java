package com.olenick.avatar.pages;

import org.jdom2.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.olenick.avatar.tests.ICare2Page;
import com.olenick.avatar.tests.PatientExperiencePage;

public class PatientExperienceLogin {

	WebDriver driver;
	WebElement username, password, submit;
	private String devBaseUrl = "http://172.16.20.210:8080/ibi_apps/signin";
	
	public PatientExperienceLogin(WebDriver driver) {
		setDriver(driver);
	}
	
	public PatientExperienceLogin() {
	}
	
	public PatientExperienceLogin detectElements() {
		try{
			setUsername(driver.findElement(By.id("SignonUserName")));
			setPassword(driver.findElement(By.id("SignonPassName")));
			setSubmit(driver.findElement(By.id("SignonbtnLogin")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return the username
	 */
	public WebElement getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(WebElement username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public WebElement getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(WebElement password) {
		this.password = password;
	}

	/**
	 * @return the submit
	 */
	public WebElement getSubmit() {
		return submit;
	}

	/**
	 * @param submit the submit to set
	 */
	public void setSubmit(WebElement submit) {
		this.submit = submit;
	}

	public PatientExperienceLogin open(boolean dev) {
		if (dev) { driver.get(devBaseUrl ); } else { driver.get("");}; //TODO: Agregar la url de produccion.
		return this;
	}

	public PatientExperienceLogin setUser(String user) {
		username.clear();
		username.sendKeys(user);
		return this;
	}
	
	public PatientExperienceLogin setPass(String pass) {
		password.clear();
		password.sendKeys(pass);
		return this;
	}
	
	public ICare2 login(String user, String pass){
		setUser(user);
		setPass(pass);
		submit.click();
		ICare2 iCare2Page = new ICare2(driver, true);
		//PatientExperience patientExperiencePage = new PatientExperience(driver, true);
		return iCare2Page;
	}

}
