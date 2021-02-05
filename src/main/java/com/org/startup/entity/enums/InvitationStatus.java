package com.org.startup.entity.enums;

public enum InvitationStatus {
	NOTVERIFIED("NOTVERIFIED"),
	VERIFIED("VERIFIED");
	
	private String status;
	 
	InvitationStatus(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }
}
