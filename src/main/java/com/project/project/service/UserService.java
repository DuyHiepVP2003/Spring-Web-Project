package com.project.project.service;

import com.project.project.model.User;
import com.project.project.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private HttpSession session;
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public void save(User user){
        userRepository.save(user);
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public boolean isUserLogin(){
        User user = (User) session.getAttribute("user");
        if (user == null) return false;
        return true;
    }
    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user==null|| user.isEnabled()) return false;
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registation";
        String senderName = "Group Name";
        String mailContent = "<p>Dear " + user.getName() +",</p>";
        mailContent+="<p>Please click the link below to verify to your registation:</p>";
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        mailContent+="<h3><a href=\""+ verifyURL + "\">VERIFY</a></h3>";
        mailContent+="<p>Thank you<br>The Group Name</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("buiduyhiep0@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }
}
