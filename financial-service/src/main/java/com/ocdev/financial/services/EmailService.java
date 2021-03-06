package com.ocdev.financial.services;

import javax.mail.MessagingException;

public interface EmailService
{
	/**
	 * Envoi d'un email au format HTML
	 * @param to Le destinataire
	 * @param content Le contenu de l'email
	 * @param subject Le sujet de l'email
	 * @param from L'expediteur
	 * @throws MessagingException
	 */
	public void sendEmailHtml(String to, String content, String subject, String from) throws MessagingException;
}