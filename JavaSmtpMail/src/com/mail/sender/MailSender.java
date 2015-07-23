package com.mail.sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.mail.Const;
import com.mail.auth.MailAuthenticator;
import com.mail.entity.MailInfo;

public class MailSender {

	public void sendMail(MailInfo mailInfo) throws Exception {
		//check mailInfo status
		checkMailInfo(mailInfo);
		//authenticator
	    MailAuthenticator authenticator = null;
	    //smtp properties
	    Properties properties = new Properties();
        //get sresource
        properties.setProperty(Const.SMTPHOST, mailInfo.getHost());
        properties.setProperty(Const.SMTPPORT, mailInfo.getPort());
        properties.setProperty(Const.SMTPAUTH, mailInfo.getAuth());
        //enable authenticator
	    authenticator = new MailAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
	    //mail session
	    Session mailSession = Session.getDefaultInstance(properties, authenticator);
	    //enable mail session debug info
	    mailSession.setDebug(true);   
	    //mail type Mime
	    Message mailMessage = new MimeMessage(mailSession);
	    //time to send mail
		mailMessage.setSentDate(mailInfo.getDate());
	    //get & set from address
	    Address from = new InternetAddress(mailInfo.getFrom());
	    mailMessage.setFrom(from);
	    //get & set to address
	    String[] targetAddress;
	    if(mailInfo.getTo() != null){
	    	targetAddress = mailInfo.getTo();
		    String toTargets = getMailList(targetAddress);
			InternetAddress[] to = InternetAddress.parse(toTargets);
		    mailMessage.setRecipients(Message.RecipientType.TO, to);
		    targetAddress = null;
	    }
	    //get & set cc address
	    if(mailInfo.getCc() != null){
	    	targetAddress = mailInfo.getCc();
	    	String ccTargets = getMailList(targetAddress);
	    	InternetAddress[] cc = InternetAddress.parse(ccTargets);
		    mailMessage.setRecipients(Message.RecipientType.CC, cc);
		    targetAddress = null;
	    }
	    //get & set bcc address
	    if(mailInfo.getBcc() != null){
	    	targetAddress = mailInfo.getBcc();
	    	String bccTargets = getMailList(targetAddress);
	    	InternetAddress[] bcc = InternetAddress.parse(bccTargets);
		    mailMessage.setRecipients(Message.RecipientType.CC, bcc);
		    targetAddress = null;
	    }
	    //mail title
	    mailMessage.setSubject(mailInfo.getSubject());
	    
	    MimeBodyPart contentPart = null;
	    List<MimeBodyPart> imagePart = null;
	    List<MimeBodyPart> attatchPart = null;
	    //get mail content
	    if(mailInfo.getContent() != null){
	    	contentPart = createContent(mailInfo.getContent(), mailInfo.getContentType());	
	    }
	    //get mail image
	    if(mailInfo.getImageFilePath() != null){
	    	imagePart = createImage(mailInfo.getImageFilePath(), mailInfo.getCid());	
	    }
	    //get mail attatchment
	    if(mailInfo.getAttatchFilePath() != null){
	    	attatchPart = createAttatch(mailInfo.getAttatchFilePath());	
	    }
	    //set content part
	    MimeMultipart multiPart = new MimeMultipart();
	    if(contentPart != null){
	    	multiPart.addBodyPart(contentPart);	
	    }
	    //set image part
	    if(imagePart != null){
	    	if(imagePart.size() != 0){
		    	for (int i = 0; i < imagePart.size(); i++) {
		    		multiPart.addBodyPart(imagePart.get(i));
				}
		    	multiPart.setSubType(Const.RELATED);	
		    }
	    }
	    //combine content & image
	    MimeBodyPart mainBodyPart = new MimeBodyPart();
	    mainBodyPart.setContent(multiPart);
	    //main return part
	    MimeMultipart mainMultiPart = new MimeMultipart();
	    mainMultiPart.addBodyPart(mainBodyPart);
	    //set attatch part
	    if(attatchPart != null){
	    	if(attatchPart.size() != 0){
		    	for (int i = 0; i < attatchPart.size(); i++) {
		    		mainMultiPart.addBodyPart(attatchPart.get(i));
				}
			    mainMultiPart.setSubType(Const.MIXED);	    	
		    }
	    }
	    //set main part
	    mailMessage.setContent(mainMultiPart);
	    //save
	    mailMessage.saveChanges();
	    //send mail
	    Transport trans = null;
        trans = mailSession.getTransport();  
        trans.connect(mailInfo.getHost(), mailInfo.getUsername(), mailInfo.getPassword());  
        trans.sendMessage(mailMessage, mailMessage.getAllRecipients());  
        trans.close();  
	}
	
	/**
	 * check mailInfo status
	 * @param mailInfo
	 * @throws Exception
	 */
	private void checkMailInfo(MailInfo mailInfo) throws Exception{
		//from
		if(mailInfo.getFrom() == null){
			throw new Exception("NULLPOINT:MAILINFO.FROM IS NULL");
		}
		//to
		if(mailInfo.getTo() == null){
			throw new Exception("NULLPOINT:MAILINFO.TO IS NULL");
		}
		//content
		if(mailInfo.getContent() == null){
			throw new Exception("NULLPOINT:MAILINFO.CONTENT IS NULL");
		}
	}
	
	/**
	 * create contentPart
	 * @param content
	 * @param type
	 * @return
	 * @throws MessagingException
	 */
	private MimeBodyPart createContent(String content, String type) throws MessagingException{
		//content
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(content,type);
		return text;
	}
	
	/**
	 * create imagePart
	 * @param filePath
	 * @param cid
	 * @return
	 * @throws MessagingException
	 */
	private List<MimeBodyPart> createImage(String[] filePath, String[] cid) throws MessagingException{
		//image
		List<MimeBodyPart> imageList = new ArrayList<MimeBodyPart>();
		for (int i = 0; i < filePath.length; i++) {
			MimeBodyPart image = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(new FileDataSource(filePath[i]));
			image.setDataHandler(dataHandler);
			image.setContentID(cid[i]);	
			imageList.add(image);
			image = null;
		}
		return imageList;
	}
	
	/**
	 * create attatchPart
	 * @param filePath
	 * @return
	 * @throws MessagingException
	 */
	private List<MimeBodyPart> createAttatch(String[] filePath) throws MessagingException{
		//attatchment
		List<MimeBodyPart> attatchList = new ArrayList<MimeBodyPart>();
		for (int i = 0; i < filePath.length; i++) {
			MimeBodyPart attatch = new MimeBodyPart();
			DataHandler attatchDataHandler = new DataHandler(new FileDataSource(filePath[i]));
			attatch.setDataHandler(attatchDataHandler);
			String fileName = attatchDataHandler.getName();
			attatch.setFileName(fileName);
			attatchList.add(attatch);
			attatch = null;
		}
		return attatchList;
	}
	
    /**
     * change mailArray to String
     * @param mailArray
     * @return
     */
    private String getMailList(String[] mailArray) { 
    	StringBuffer toList = new StringBuffer(); 
    	int length = mailArray.length; 
    	if (mailArray != null && length < 2) { 
    		toList.append(mailArray[0]); 
    	} else { 
    		for (int i = 0; i < length; i++) { 
    			toList.append(mailArray[i]); 
    			if (i != (length - 1)) { 
    				toList.append(","); 
    			} 
    		} 
    	} 
    	return toList.toString(); 
	} 
    
    public static void main(String[] args) {
		
	}
 
}
