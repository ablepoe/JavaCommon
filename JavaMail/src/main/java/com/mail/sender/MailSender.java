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
import javax.mail.internet.MimeUtility;

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
//        trans.sendMessage(mailMessage, mailMessage.getAllRecipients());  
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
	
	public void myContent(Message mailMessage) throws Exception{
			//创建邮件的正文
	       MimeBodyPart text = new MimeBodyPart();
	       // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
	       text.setContent("世界上最复杂的邮件<img src='cid:a'>","text/html;charset=gb2312");
	       //创建图片
	       MimeBodyPart img = new MimeBodyPart();
	       DataHandler dh = new DataHandler(new FileDataSource("D:/1.jpg"));
	       img.setDataHandler(dh);
	       //创建图片的一个表示用于显示在邮件中显示
	       img.setContentID("a");
	      
	       //创建附件
	       MimeBodyPart attch = new MimeBodyPart();
	       DataHandler attatchDataHandler = new DataHandler(new FileDataSource("D:/2.jpg"));
	       attch.setDataHandler(attatchDataHandler);
	       String filename1 = attatchDataHandler.getName();
	        // MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
	       attch.setFileName(MimeUtility.encodeText(filename1));
	     //关系   正文和图片的
	       MimeMultipart mm = new MimeMultipart();
	       mm.addBodyPart(text);
	       mm.addBodyPart(img);
	       mm.setSubType("related");//设置正文与图片之间的关系
	       //图班与正文的 body
	       MimeBodyPart all = new MimeBodyPart();
	       all.setContent(mm);
	       //附件与正文（text 和 img）的关系
	       MimeMultipart mm2 = new MimeMultipart();
	       mm2.addBodyPart(all);
	       mm2.addBodyPart(attch);
	       mm2.setSubType("mixed");//设置正文与附件之间的关系
	      
	       mailMessage.setContent(mm2);
	       mailMessage.saveChanges(); //保存修改
//	       Transport.send(message);//发送邮件
	}
	
    public MimeBodyPart createContent(String[] fileName)  
            throws Exception {  
        // 用于保存最终正文部分  
        MimeBodyPart contentBody = new MimeBodyPart();
        // 用于组合文本和图片，"related"型的MimeMultipart对象  
        MimeMultipart contentMulti = new MimeMultipart("related");
 
        // 正文的图片部分  
        MimeBodyPart jpgBody;
        for (int i = 0; i < fileName.length; i++) {
        	jpgBody = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileName[i]);
            jpgBody.setDataHandler(new DataHandler(fds));
            jpgBody.setFileName(fileName[i]);
            contentMulti.addBodyPart(jpgBody);
		}
 
        // 将上面"related"型的 MimeMultipart 对象作为邮件的正文  
        contentBody.setContent(contentMulti);
        return contentBody;
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
		
		MailInfo mailInfo = new MailInfo();
//		String[] to = {"2223108045@qq.com","75169824@qq.com"};
//		mailInfo.setTo(to);
		mailInfo.setFrom("2223108045@qq.com");
		String[] to = {"2223108045@qq.com"};
		mailInfo.setTo(to);
		mailInfo.setUsername("2223108045@qq.com");
		mailInfo.setPassword("Aa123456");
//		mailInfo.setSubject("here is my test");
		mailInfo.setContent("世界上最复杂的邮件 <tr> <img src='cid:a'> <td> <img src='cid:b'>");
		String[] images ={"D:/1.jpg","D:/2.jpg"};
//		mailInfo.setImageFilePath(images);
		String[] imagecids = {"a","b"};
//		mailInfo.setCid(imagecids);
//		mailInfo.setAttatchFilePath("D:/2.jpg");
//		String[] attatchs ={"D:/1.jpg","D:/2.jpg"};
//		mailInfo.setAttatchs(attatchs);
		MailSender ms = new MailSender();
		try {
			ms.sendMail(mailInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
