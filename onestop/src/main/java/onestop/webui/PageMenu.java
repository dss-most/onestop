package onestop.webui;

public class PageMenu {

	private Boolean homeMenu;
	private Boolean contactMenu;
	private Boolean serviceMenu;
	private Boolean testingSubMenu;
	private Boolean calibrationSubMenu;
	private Boolean labSubMenu;
	private Boolean othersSubMenu;
	
	
	public PageMenu() {
		super();
		this.homeMenu = false;
		this.contactMenu = false;
		this.serviceMenu =false;
		this.testingSubMenu = false;
		this.calibrationSubMenu = false;
		this.labSubMenu = false;
		this.othersSubMenu = false;
	}
	public Boolean getHomeMenu() {
		return homeMenu;
	}
	public void setHomeMenu(Boolean homeMenu) {
		this.homeMenu = homeMenu;
	}
	public Boolean getContactMenu() {
		return contactMenu;
	}
	public void setContactMenu(Boolean contactMenu) {
		this.contactMenu = contactMenu;
	}
	public Boolean getServiceMenu() {
		return serviceMenu;
	}
	public void setServiceMenu(Boolean serviceMenu) {
		this.serviceMenu = serviceMenu;
	}
	public Boolean getTestingSubMenu() {
		return testingSubMenu;
	}
	public void setTestingSubMenu(Boolean testingSubMenu) {
		this.testingSubMenu = testingSubMenu;
	}
	public Boolean getCalibrationSubMenu() {
		return calibrationSubMenu;
	}
	public void setCalibrationSubMenu(Boolean calibrationSubMenu) {
		this.calibrationSubMenu = calibrationSubMenu;
	}
	public Boolean getLabSubMenu() {
		return labSubMenu;
	}
	public void setLabSubMenu(Boolean labSubMenu) {
		this.labSubMenu = labSubMenu;
	}
	public Boolean getOthersSubMenu() {
		return othersSubMenu;
	}
	public void setOthersSubMenu(Boolean othersSubMenu) {
		this.othersSubMenu = othersSubMenu;
	}
	
	
	
	
}
