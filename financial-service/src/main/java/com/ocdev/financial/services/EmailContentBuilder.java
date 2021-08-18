package com.ocdev.financial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ocdev.financial.entities.Flight;

/**
 * Classe de création de contenus d'emails
 * @author C.Orsini
 *
 */
@Service
public class EmailContentBuilder
{
	@Autowired TemplateEngine templateEngine;
	
	@Value("${app.param.societe}") 
	private String _societe="";
	
	@Value("${app.param.emailcontact}") 
	private String _emailContact="";
	
	/**
	 * Création du contenu d'un email de facture
	 * 
	 * Le contenu est créé par le templateEngine
	 * @param givenName Le prénom du destinataire
	 * @param familyName Le nom du destinataire
	 * @param email L'email du destinataire
	 * @return Le texte à mettre dans le contenu de l'email
	 */
	public String buildInvoiceEmail(String givenName, String familyName, String email, Flight invoice)
	{
		Context context = new Context();
        context.setVariable("societe", _societe);
        context.setVariable("givenName", givenName);
        context.setVariable("familyName", familyName);
        context.setVariable("email", _emailContact);
        context.setVariable("invoice", invoice);
        
        return templateEngine.process("invoice", context);
	}
}
