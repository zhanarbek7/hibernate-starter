package com.joe;

import com.joe.converter.BirthdayConverter;
import com.joe.entity.Birthday;
import com.joe.entity.PersonalInfo;
import com.joe.entity.Role;
import com.joe.entity.User;
import com.joe.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import java.sql.Date;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
            User user = User.builder()
                    .username("petr@gmail.com")
                    .role(Role.ADMIN)
                    .personalInfo(PersonalInfo.builder()
                            .lastname("Petrov")
                            .firstname("Petr")
                            .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                            .build())
                    .build();

            try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
                Session session1 = sessionFactory.openSession();
                Session session2 = sessionFactory.openSession();
                try (session1) {
                    session1.beginTransaction();

                    session1.saveOrUpdate(user);

                    session1.getTransaction().commit();
                }
                try (session2) {
                    PersonalInfo key = PersonalInfo.builder()
                            .lastname("Petrov")
                            .firstname("Petr")
                            .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                            .build();

                    User user2 = session2.get(User.class, key);
                    System.out.println(user2);
                }
            } catch (Exception exception) {
                throw exception;
            }
    }
}
