package com.ovh.api;

public class OvhApiException extends Exception {
	
	public enum OvhApiExceptionCause {
		CONFIG_ERROR,
		INTERNAL_ERROR,
		RESOURCE_NOT_FOUND,
		RESOURCE_CONFLICT_ERROR,
		BAD_PARAMETERS_ERROR,
		AUTH_ERROR,
		API_ERROR;
	};
	
	private final OvhApiExceptionCause ovhCause;

	public OvhApiException(String message, OvhApiExceptionCause ovhCause) {
		super(message);
		this.ovhCause = ovhCause;
	}

	public OvhApiExceptionCause getOvhCause() {
		return ovhCause;
	}

	@Override
	public String toString() {
		return "OvhApiException [ovhCause=" + ovhCause + "] : " + getLocalizedMessage();
	}


}
