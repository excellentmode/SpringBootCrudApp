package org.example.springbootcrudapp.service.data;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.example.springbootcrudapp.entity.EmailData;
import org.example.springbootcrudapp.entity.PhoneData;
import org.example.springbootcrudapp.entity.UserData;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<UserData> hasNameLike(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;

            query.distinct(true);
            return cb.like(root.get("name"), name + "%");
        };
    }

    public static Specification<UserData> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null) return null;

            query.distinct(true);
            Join<UserData, EmailData> emailJoin = root.join("emails", JoinType.INNER);
            return cb.equal(emailJoin.get("email"), email);
        };
    }

    public static Specification<UserData> hasPhone(String phone) {
        return (root, query, cb) -> {
            if (phone == null) return null;

            query.distinct(true);
            Join<UserData, PhoneData> phoneJoin = root.join("phones", JoinType.INNER);
            return cb.equal(phoneJoin.get("phone"), phone);
        };
    }

    public static Specification<UserData> bornAfter(LocalDate date) {
        return (root, query, cb) -> {
            if (date == null) return null;

            query.distinct(true);
            return cb.greaterThan(root.get("dateOfBirth"), date);
        };
    }
}