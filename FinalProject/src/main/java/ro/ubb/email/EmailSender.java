package ro.ubb.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;

@Component
public class EmailSender {

	@Value("${sendinblue.key}")
	private String sendinblueKey;

	@Value("${sendinblue.template.signup}")
	public Long signupTemplate;

	public static final String CODE = "code";

	@PostConstruct
	private void init() {
		ApiClient defaultClient = Configuration.getDefaultApiClient();
		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
		apiKey.setApiKey(sendinblueKey);
	}

	@Async
	public void sendSendinblue(Long templateId, List<String> to, Map<String, Object> params) {
		TransactionalEmailsApi api = new TransactionalEmailsApi();
		List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
		for (String email : to) {
			SendSmtpEmailTo sendSmtpEmailTo = new SendSmtpEmailTo();
			sendSmtpEmailTo.setEmail(email);
			toList.add(sendSmtpEmailTo);

		}
		SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
		sendSmtpEmail.setTo(toList);
		sendSmtpEmail.setParams(params);
		sendSmtpEmail.setTemplateId(templateId);
		try {
			CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
