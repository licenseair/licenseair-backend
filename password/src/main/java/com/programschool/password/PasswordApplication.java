package com.programschool.password;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PasswordApplication {

  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (args.length == 1) {
      System.out.println(encoder.encode(args[0]));
    } else {
      System.out.println("参数不正确，CMD： ./gradlew password:bootRun --args=\"password\"");
    }

    SpringApplication.run(PasswordApplication.class, args);
  }
}
