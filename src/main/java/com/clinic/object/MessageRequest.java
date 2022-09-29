package com.clinic.object;

public class MessageRequest {
	
	private String messaging_product;
	private String recipient_type;
	private String to;
	private String type;
	private MessageTemplateRq template;
	
	public MessageRequest (String to) {
		this.messaging_product = "whatsapp";
		this.recipient_type = "individual";
		this.to = to;
		this.type = "template";
		
		MessageTemplateRq templateRq = new MessageTemplateRq();
		templateRq.setName("pengingat_jadwal_imunisasi");
		MessageLanguageRq languageRq = new MessageLanguageRq();
		languageRq.setCode("en_US");
		templateRq.setLanguage(languageRq);
		
		this.template = templateRq;
		
	}
	
	public String getMessaging_product() {
		return messaging_product;
	}
	public void setMessaging_product(String messaging_product) {
		this.messaging_product = messaging_product;
	}
	public String getRecipient_type() {
		return recipient_type;
	}
	public void setRecipient_type(String recipient_type) {
		this.recipient_type = recipient_type;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MessageTemplateRq getTemplate() {
		return template;
	}
	public void setTemplate(MessageTemplateRq template) {
		this.template = template;
	}

}
