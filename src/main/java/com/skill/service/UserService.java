package com.skill.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skill.DAO.UserDAO;
import com.skill.configuration.EmailConfiguration;
import com.skill.entity.UserDetails;
@Service
public class UserService {
	
	@Autowired
	UserDAO dao;
	
	@Autowired
	EmailConfiguration emailConfig;
	
	public ResponseEntity<String> insertData(UserDetails details) {

	    if (!isEmailVerified(details.getEmailid())) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("OTP not verified");
	    }
	    
	    List<UserDetails> allUsers = dao.getAllUsers();
	    for (UserDetails user : allUsers) {
	        if (user.getEmailid().equals(details.getEmailid())) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body("Already Having Account");
	        }
	    }


	    UserDetails userData = dao.insertData(details);
	    if (userData != null) {
	        welcomeMail(userData);


	        clearVerification(details.getEmailid());

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body("Registration Successful");
	    }

	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("An error occurred");
	}

	
	public ResponseEntity<List<UserDetails>> getAllUsers() {
		
		List<UserDetails> users = dao.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	public ResponseEntity<UserDetails> getUser(String user,String password) {
		
		UserDetails userDetails = dao.getUser(user,password);
		if(userDetails != null)
			return ResponseEntity.status(HttpStatus.OK).body(userDetails);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	public ResponseEntity<String> updateData(UserDetails details) {
		
		UserDetails updatedData = dao.updateData(details);
		if (updatedData!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Updated Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Updation Failed");
	}
	
	public ResponseEntity<String> deleteUser(String email) {
		
		if (dao.deleteUser(email)!=0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data deleted");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
		}
	}
	
	public ResponseEntity<UserDetails> getUser(String email) {
		
		Optional<UserDetails> user = dao.getUser(email);
		if(!user.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	public ResponseEntity<String> mailToPartner(String partnerGmail, String userGmail) {
		Optional<UserDetails> partnerDetails = dao.getUser(partnerGmail);
		Optional<UserDetails> userDetails = dao.getUser(userGmail);
		UserDetails partner = partnerDetails.get();
		UserDetails user = userDetails.get();
		String subject = "🛠 New Service Request for You on SkillScanner!";
		String body = "👋 Hello " + partner.getName() + ",\n\n" +
	              "You have a new service request from " + user.getName() + ".\n\n" +
	              "📝 Requester Details:\n" +
	              "• 👤 Name: " + user.getName() + "\n" +
	              "• 📱 Mobile: " + user.getMobile() + "\n" +
	              "• 📍 Location: " + user.getLocation() + ", " + user.getCity() + "\n\n" +
	              "⚡ Why this matters:\n" +
	              "We value your skills and want to connect you with people who need your skills. " +
	              "Responding quickly can make a big difference and help you to grow. \n\n" +
	              "📌 Next Steps:\n" +
	              "Please reach out to the requester as soon as possible to provide your service. " +
	              "Your expertise is highly appreciated!\n\n" +
	              "Thank you for being a trusted part of the SkillScanner community. 🌟\n\n" +
	              "Best regards,\n" +
	              "SkillScanner Team ✨";
		boolean sendMail = emailConfig.sendMail(partnerGmail, subject, body);
		if(sendMail)
			return ResponseEntity.status(HttpStatus.OK).body("Mail Sent Successfully");
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mail not delivered");
	}
	
	private void welcomeMail(UserDetails user) {
		String subject = "🎉 Welcome to SkillScanner – Registration Successful!";
        String body = "👋 Dear " + user.getName() + ",\n\n"
        	+"Welcome to SkillScanner! 🎉\n"
            + "Your registration has been completed successfully, and we’re happy to have you with us.\n\n"
            + "SkillScanner helps you find trusted professionals for everyday services like electrical work, plumbing, appliance repair, laptop servicing, and more—all in one place.\n"
            + "If you have skills to offer, you can also choose to become a SkillScanner Partner and showcase your expertise to reach the right audience. This is completely optional and available whenever you’re ready.\n\n"
            + "📌 This is a demo application created for trial and educational purposes.\n"
            + "🔍 Search for partners in your area\n"
            + "🛠️ Register as a partner with your skills \n\n"
            + "Thank you for joining us! 🙏\n\n"
            + "Team SkillScanner ✨\n\n"
            + "“Every skill matters.” 💡";
        emailConfig.sendMail(user.getEmailid(), subject, body);
	}
	
	
	
	private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet();

    public boolean isEmailAlreadyRegistered(String email) {
        Optional<UserDetails> user = dao.getUser(email);
        return user.isPresent();
    }

    public ResponseEntity<String> sendOtp(String email) {
    	
    	if (isEmailAlreadyRegistered(email)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already registered, please login");
        }
    	
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStore.put(email, otp);
        String subject = "🎉 SkillScanner: Verify Your Email ✅";
        String body = "Hello! 👋\n\nPlease use the OTP below to verify your email and complete registration: 🔑 "+otp;

        
        boolean sendMail = emailConfig.sendMail(email, subject, body);
        if(sendMail)
			return ResponseEntity.status(HttpStatus.OK).body("OTP Sent");
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP Not Sent");
    }


    public ResponseEntity<String> verifyOtp(String email, String otp) {
    	
    	boolean verified = otpVerification(email, otp);

        if (verified) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
    
    public boolean otpVerification(String email, String otp) {
    	String storedOtp = otpStore.get(email);

        if (storedOtp == null) return false;
        if (!storedOtp.equals(otp)) return false;

        otpStore.remove(email);
        verifiedEmails.add(email);
        return true;
    }


    public boolean isEmailVerified(String email) {
        return verifiedEmails.contains(email);
    }


    public void clearVerification(String email) {
        verifiedEmails.remove(email);
    }
	
    
    public ResponseEntity<String> sendResetOtp(String email) {

        Optional<UserDetails> user = dao.getUser(email);

        if (user.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email not registered");
        }

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStore.put(email, otp);

        String subject = "🔒 SkillScanner: Password Reset OTP";
        String body = "Hello! 👋\n\nYour OTP to reset your SkillScanner password is: 🔑 " + otp;

        boolean sendMail = emailConfig.sendMail(email, subject, body);
        if(sendMail)
			return ResponseEntity.status(HttpStatus.OK).body("OTP Sent");
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP Not Sent");
    }

}
