package com.joe;

import com.joe.converter.BirthdayConverter;
import com.joe.entity.Birthday;
import com.joe.entity.Role;
import com.joe.entity.User;
import com.joe.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {

        User user = User.builder()
                .username("zhanarbek@gmail.com")
                .firstname("Zhanarbek")
                .lastname("Abdurasulov")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session1 = sessionFactory.openSession()){
                session1.beginTransaction();
                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

            try(Session session2 = sessionFactory.openSession()){
                session2.beginTransaction();

                user.setFirstname("Sveta");
//                session2.delete(user);
                // refresh gets data from db and sets it to object
//                session2.refresh(user);

                // merge does change on data in db and returns changed object
                session2.merge(user);
                session2.getTransaction().commit();
            }
        }
    }
}
