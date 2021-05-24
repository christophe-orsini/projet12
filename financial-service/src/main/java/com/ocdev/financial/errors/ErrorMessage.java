package com.ocdev.financial.errors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information au sujet des erreurs retournées par les requètes API")
public class ErrorMessage
{
	@ApiModelProperty("Code d'erreur (habituellement code de statut HTTP)")
	private int statusCode;
	@ApiModelProperty("Raison de l'erreur")
	private String reason;
	@ApiModelProperty("Message d'erreur")
	private String message;
	
	public ErrorMessage(int statusCode, String reason, String message)
	{
		super();
		this.statusCode = statusCode;
		this.reason = reason;
		this.message = message;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public String getReason()
	{
		return reason;
	}

	public String getMessage()
	{
		return message;
	}
}
