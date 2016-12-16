package com.liee.core.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtil {
	private String from = "";
	private String to = "";
	private String host = "";
	private String fileName = "";
	private String subject = "";
	private String content = "";
	private String password = "";
	private ArrayList<String> file = new ArrayList<String>();

	public static boolean sendTextMail(String to, String subject, String content) {
		// System.out.println("sendTextMail:"+to+","+subject+","+content);
														  
		MailUtil objMail = new MailUtil("smtp.live.com", "asia-top100@hotmail.com", to, subject, content);
		objMail.setPassword("aliyungao168");
		boolean rc = objMail.send();
		return rc;
	}

	public MailUtil(String smtpServer, String from, String to) {
		this(smtpServer, from, to, "", null);
	}

	public MailUtil(String smtpServer, String from, String to, String subject) {
		this(smtpServer, from, to, subject, null);
	}

	public MailUtil(String smtpServer, String from, String to, String subject, String content) {
		this.to = to;
		this.from = from;
		this.host = smtpServer;
		this.subject = subject;
		this.content = content;
	}

	public void addAttachment(String fname) {
		file.add(fname);
	}

	public boolean send() {
		return send(false);
	}

	public boolean send(boolean isHtml) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, null);
		Transport transport = null;
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			String toArray[] = to.split(";");
			for (int i = 0; i < toArray.length; i++) {
				toArray[i] = toArray[i].trim();
				if (toArray[i].length() > 0)
					msg.addRecipients(Message.RecipientType.TO, toArray[i]);
			}
			msg.setSubject(MimeUtility.encodeText(subject));
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			if (content != null && content.length() > 0) {
				if (isHtml)
					mbp.setContent(content, "text/html;charset=utf-8");
				else
					mbp.setText(content);
				mp.addBodyPart(mbp);
			}
			for (int i = 0; i < file.size(); i++) {
				mbp = new MimeBodyPart();
				fileName = (String) file.get(i);
				FileDataSource fds = new FileDataSource(fileName);
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(MimeUtility.encodeText(fds.getName()));
				mp.addBodyPart(mbp);
			}
			file.clear();
			msg.setContent(mp);
			msg.setSentDate(new Date());
			transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception mex) {
			System.out.println(this.getClass().getName() + ": " + mex.getMessage());
			return false;
		} finally {
			try {
				if (transport != null && transport.isConnected())
					transport.close();
			} catch (Exception e) {
			}
		}

		return true;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public static void main(String[] args) {
		
		
		 String subject="Asia Top 100 Golf Courses Award submitted";
		 String content="To 李靖：\n	"
				 		+"          您为XXX球会YYY球场提名了ZZZZ，提名已经提交成功。组委会将对资料进行核实，一旦提名通过，组委会将安排工作人员与您联系，感谢您的参与和支持。\n\n "
				 		+"          You nominate XXX球会YYY球场 for ZZZZ  and it has been submitted successfully. The Organizing Committee will verify the information. Once the nomination through, the Organizing Committee will arrange for staff to contact you. Thank you for your participation and support.\n\n"
				 		+"                                                                                                                                                                "
				 		+"亚洲百佳球场评选组委会\n"
				 		+"                                                                                                                                                                "
				 		+"Asia Top 100 Golf Courses Award Organizing Committee ";
		 
		
		 sendTextMail("lieeking@qq.com", subject, content);
	}

}