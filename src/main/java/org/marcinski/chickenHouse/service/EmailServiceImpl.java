package org.marcinski.chickenHouse.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;
    private Environment environment;

    public EmailServiceImpl(JavaMailSender mailSender, Environment environment) {
        this.mailSender = mailSender;
        this.environment = environment;
    }

    public String address() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = addressEnumeration.nextElement();
                    if (inetAddress.isLoopbackAddress()) {
                        continue;
                    }
                    if (inetAddress.getHostAddress().contains("127.0.0.1") ||
                            inetAddress.getHostAddress().contains("localhost") ||
                            inetAddress.getHostAddress().contains("::1") ||
                            inetAddress.getHostAddress().contains("172.17.0.1") ||
                            inetAddress.getHostAddress().contains(":")) {
                        continue;
                    }
                    if (inetAddress.getHostAddress().contains("eth1")) {
                        return inetAddress.getHostAddress().split("%")[0];
                    }
                    if (inetAddress.getHostAddress().contains("%")) {
                        return inetAddress.getHostAddress().split("%")[0];
                    }
                    return inetAddress.getHostAddress();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "46.41.138.35";
    }

    @Override
    public void sendEmailWithAuthorizationLink(String to, String uuid) {
        SimpleMailMessage message = new SimpleMailMessage();

        String host = address();
        Integer port = environment.getProperty("server.port", Integer.class, 8080).intValue();
        String address = host + ":" + port;

        if (address.equals("46.41.138.35:443")){
            address = "houseofwings.com.pl";
        }

        message.setTo(to);
        message.setSubject("Aktywacja konta");
        message.setText("Witaj, aby aktywować konto wejdź w link: " +
                "https://" + address + "/registration/" + uuid);
        mailSender.send(message);
    }
}
