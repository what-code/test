package test;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SimpleMailSender {

	/**  
	  * 以文本格式发送邮件  
	  * @param mailInfo 待发送的邮件的信息  
	  */   
	    public boolean sendTextMail(MailSenderInfo mailInfo) { 
	    	  
	      // 判断是否需要身份认证   
	      MyAuthenticator authenticator = null;   
	      Properties pro = mailInfo.getProperties();  
	      /*Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.class", "SSL_FACTORY");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.auth", "true");*/
	      if (mailInfo.isValidate()) {   
	      // 如果需要身份认证，则创建一个密码验证器   
	        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());  
	        System.out.println("------>" + mailInfo.getUserName());
	      }  
	      // 根据邮件会话属性和密码验证器构造一个发送邮件的session   
	      Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
	      try {   
	      // 根据session创建一个邮件消息   
	      Message mailMessage = new MimeMessage(sendMailSession);   
	      // 创建邮件发送者地址   
	      Address from = new InternetAddress(mailInfo.getFromAddress());   
	      // 设置邮件消息的发送者   
	      mailMessage.setFrom(from);   
	      String to1 = mailInfo.getMailTo();
	      String to2 = mailInfo.getCopyMailTo();
	      
	      Address ato1 = new InternetAddress(mailInfo.getMailTo());   
	      mailMessage.setRecipient(Message.RecipientType.TO,ato1);
	      //mailMessage.setRecipients(Message.RecipientType.TO, arg1);
	      
	      Address ato2 = new InternetAddress(mailInfo.getCopyMailTo());   
	      mailMessage.addRecipient(Message.RecipientType.TO,ato2);   
	      // 设置邮件消息的主题   
	      mailMessage.setSubject(mailInfo.getSubject());   
	      // 设置邮件消息发送的时间   
	      mailMessage.setSentDate(new Date());   
	      // 设置邮件消息的主要内容   
	      String mailContent = mailInfo.getContent();   
	      mailMessage.setText(mailContent);   
	      // 发送邮件   
	      Transport.send(mailMessage);  
	      return true;   
	      } catch (MessagingException ex) {   
	          ex.printStackTrace();   
	      }   
	      return false;   
	    }   
	      
	    /**  
	      * 以HTML格式发送邮件  
	      * @param mailInfo 待发送的邮件信息  
	      */   
	    public static boolean sendHtmlMail(MailSenderInfo mailInfo){   
	      // 判断是否需要身份认证   
	      MyAuthenticator authenticator = null;  
	      Properties pro = mailInfo.getProperties();  
	      //如果需要身份认证，则创建一个密码验证器    
	      if (mailInfo.isValidate()) {   
	        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());  
	      }   
	      // 根据邮件会话属性和密码验证器构造一个发送邮件的session   
	      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);   
	      try {   
	      // 根据session创建一个邮件消息   
	      Message mailMessage = new MimeMessage(sendMailSession);   
	      // 创建邮件发送者地址   
	      Address from = new InternetAddress(mailInfo.getFromAddress());   
	      // 设置邮件消息的发送者   
	      mailMessage.setFrom(from);   
	   // 设置邮件消息的发送者   
	      mailMessage.setFrom(from);   
	      String to1 = mailInfo.getMailTo();
	      String to2 = mailInfo.getCopyMailTo();
	      
	      Address ato1 = new InternetAddress(mailInfo.getMailTo());   
	      mailMessage.setRecipient(Message.RecipientType.TO,ato1);
	      //mailMessage.setRecipients(Message.RecipientType.TO, arg1);   
	      // Message.RecipientType.TO属性表示接收者的类型为TO   
	      //mailMessage.setRecipient(Message.RecipientType.TO,to);   
	      // 设置邮件消息的主题   
	      mailMessage.setSubject(mailInfo.getSubject());   
	      // 设置邮件消息发送的时间   
	      mailMessage.setSentDate(new Date());   
	      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象   
	      Multipart mainPart = new MimeMultipart();   
	      // 创建一个包含HTML内容的MimeBodyPart   
	      BodyPart html = new MimeBodyPart();   
	      // 设置HTML内容   
	      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");   
	      mainPart.addBodyPart(html);   
	      // 将MiniMultipart对象设置为邮件内容   
	      mailMessage.setContent(mainPart);   
	      // 发送邮件   
	      Transport.send(mailMessage);   
	      return true;   
	      } catch (MessagingException ex) {   
	          ex.printStackTrace();   
	      }   
	      return false;   
	    }   

}
