package com.ocdev.financial.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RefreshScope
@RequestMapping("/financials")
@RestController
@Validated
@Api(tags = {"API de gestion des finances"})
public class FinancialController
{
	
	
	
}
