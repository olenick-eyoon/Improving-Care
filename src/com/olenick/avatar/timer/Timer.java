package com.olenick.avatar.timer;

import java.util.Date;

public class Timer {

	long 
		loginTime, 
		ic1Time, 
		ic2Time, 
		overviewTime, 
		compositeTime, 
		sideBySideTime, 
		demographicsTime,
		exportCompositeTime,
		exportSideBySideTime,
		exportDemographicsTime;
	
	/**
	 * @return the exportCompositeTime
	 */
	public long getExportCompositeTime() {
		return exportCompositeTime;
	}

	/**
	 * @param exportCompositeTime the exportCompositeTime to set
	 */
	public void setExportCompositeTime(long exportCompositeTime) {
		this.exportCompositeTime = exportCompositeTime;
	}

	/**
	 * @return the exportSideBySideTime
	 */
	public long getExportSideBySideTime() {
		return exportSideBySideTime;
	}

	/**
	 * @param exportSideBySideTime the exportSideBySideTime to set
	 */
	public void setExportSideBySideTime(long exportSideBySideTime) {
		this.exportSideBySideTime = exportSideBySideTime;
	}

	/**
	 * @return the exportDemographicsTime
	 */
	public long getExportDemographicsTime() {
		return exportDemographicsTime;
	}

	/**
	 * @param exportDemographicsTime the exportDemographicsTime to set
	 */
	public void setExportDemographicsTime(long exportDemographicsTime) {
		this.exportDemographicsTime = exportDemographicsTime;
	}

	/**
	 * @return the loginTime
	 */
	public long getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the ic1Time
	 */
	public long getIc1Time() {
		return ic1Time;
	}

	/**
	 * @param ic1Time the ic1Time to set
	 */
	public void setIc1Time(long ic1Time) {
		this.ic1Time = ic1Time;
	}

	/**
	 * @return the ic2Time
	 */
	public long getIc2Time() {
		return ic2Time;
	}

	/**
	 * @param ic2Time the ic2Time to set
	 */
	public void setIc2Time(long ic2Time) {
		this.ic2Time = ic2Time;
	}

	/**
	 * @return the overviewTime
	 */
	public long getOverviewTime() {
		return overviewTime;
	}

	/**
	 * @param overviewTime the overviewTime to set
	 */
	public void setOverviewTime(long overviewTime) {
		this.overviewTime = overviewTime;
	}

	/**
	 * @return the compositeTime
	 */
	public long getCompositeTime() {
		return compositeTime;
	}

	/**
	 * @param compositeTime the compositeTime to set
	 */
	public void setCompositeTime(long compositeTime) {
		this.compositeTime = compositeTime;
	}

	/**
	 * @return the sideBySideTime
	 */
	public long getSideBySideTime() {
		return sideBySideTime;
	}

	/**
	 * @param sideBySideTime the sideBySideTime to set
	 */
	public void setSideBySideTime(long sideBySideTime) {
		this.sideBySideTime = sideBySideTime;
	}

	/**
	 * @return the demographicsTime
	 */
	public long getDemographicsTime() {
		return demographicsTime;
	}

	/**
	 * @param demographicsTime the demographicsTime to set
	 */
	public void setDemographicsTime(long demographicsTime) {
		this.demographicsTime = demographicsTime;
	}

	public void resetTimer() {
		this.compositeTime = 0;
		this.demographicsTime = 0;
		this.ic1Time = 0;
		this.ic2Time = 0;
		this.loginTime = 0;
		this.overviewTime = 0;
		this.sideBySideTime = 0;
		this.exportCompositeTime = 0;
		this.exportDemographicsTime = 0;
		this.exportSideBySideTime = 0;
	}
	
	public long setStartTime() {
		Date startDate2 = new Date();
		return startDate2.getTime();
	}
	
	public long setEndTime() {
		Date endDate2 = new Date();
		return endDate2.getTime();
	}

}
