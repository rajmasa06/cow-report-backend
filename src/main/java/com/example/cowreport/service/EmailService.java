package com.example.cowreport.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.cowreport.model.CowReport;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.server.base-url:http://localhost:8080}")
    private String serverBaseUrl;

    private static final String RAJMASA_EMAIL = "rajmasa06@gmail.com";

    public void sendReportAlert(CowReport report) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(RAJMASA_EMAIL);
            message.setSubject("URGENT: New Cow Report - ID: " + report.getId());

            // Create Action Link
            String actionLink = serverBaseUrl + "/api/cow/status/update/" + report.getId();

            // Avoid duplicate base URL
            String fullImageUrl = report.getImageUrl().startsWith("http")
                    ? report.getImageUrl()
                    : serverBaseUrl + report.getImageUrl();

            String emailBody = "Dear Rajmasa,\n\n"
                    + "A **new cow report** has been submitted by a user.\n\n"
                    + "--- REPORT DETAILS ---\n"
                    + "Report ID: " + report.getId() + "\n"
                    + "Submitted By: **" + report.getUserName() + "**\n"
                    + "User Contact: " + report.getUserEmail() + "\n"
                    + "Location: " + report.getCurrentLocation() + "\n"
                    + "Image Link: " + fullImageUrl + "\n\n"
                    + "--- ACTION REQUIRED ---\n"
                    + "After cleaning, please click the link below to mark this report as **DONE**:\n"
                    + actionLink;

            message.setText(emailBody);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error sending email alert to Rajmasa: " + e.getMessage());
        }
    }
}

//package com.example.cowreport.service;
//
//import org.springframework.beans.factory.annotation.Value; // Import kiya gaya
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import com.example.cowreport.model.CowReport;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    // Production mein URL application.properties se load hoga
//    // Default value localhost:8080 hai, jise badalna zaroori hai deployment par
//    @Value("${app.server.base-url:http://localhost:8080}") 
//    private String serverBaseUrl;
//    
//    // Rajmasa ka email address
//    private static final String RAJMASA_EMAIL = "rajmasa06@gmail.com"; 
//
//    public void sendReportAlert(CowReport report) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(RAJMASA_EMAIL); 
//            message.setSubject("URGENT: New Cow Report - ID: " + report.getId());
//            
//            // --- Dedicated Link Creation ---
//            // Rajmasa jab is link par click karega to status 'Done' ho jayega
//            String actionLink = serverBaseUrl + "/api/cow/status/update/" + report.getId();
//            
//            // Image Link ko public URL banane ke liye serverBaseUrl joda gaya hai
//            String fullImageUrl = serverBaseUrl + report.getImageUrl();
//            
//            // **Yahan poora data email body mein shamil kiya gaya hai**
//            String emailBody = "Dear Rajmasa,\n\n"
//                             + "A **new cow report** has been submitted by a user.\n\n"
//                             + "--- REPORT DETAILS ---\n"
//                             + "Report ID: " + report.getId() + "\n"
//                             + "Submitted By: **" + report.getUserName() + "**\n"
//                             + "User Contact: " + report.getUserEmail() + "\n"
//                             + "Location: " + report.getCurrentLocation() + "\n"
//                             + "Image Link: " + fullImageUrl + "\n\n"
//                             + "--- ACTION REQUIRED ---\n"
//                             + "After cleaning, please click the link below to mark this report as **DONE**:\n"
//                             + actionLink;
//
//            message.setText(emailBody);
//            mailSender.send(message);
//        } catch (Exception e) {
//            System.err.println("Error sending email alert to Rajmasa: " + e.getMessage());
//        }
//    }
//}
