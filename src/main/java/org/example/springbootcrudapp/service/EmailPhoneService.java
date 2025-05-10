package org.example.springbootcrudapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.model.EmailDataModel;
import org.example.springbootcrudapp.model.PhoneDataModel;
import org.example.springbootcrudapp.model.UserModel;
import org.example.springbootcrudapp.service.cache.UserCacheService;
import org.example.springbootcrudapp.service.data.EmailDataService;
import org.example.springbootcrudapp.service.data.PhoneDataService;
import org.example.springbootcrudapp.service.data.UserDataService;
import org.example.springbootcrudapp.utils.SecurityUtil;
import org.springframework.stereotype.Service;

//todo распилить на разные сервисы

@Service
@RequiredArgsConstructor
public class EmailPhoneService {
    private final UserDataService userDataService;
    private final EmailDataService emailDataService;
    private final PhoneDataService phoneDataService;
    private final UserCacheService userCacheService;

    @Transactional
    public void addEmail(String email) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();

        if (emailDataService.existsByEmail(email)) {
            throw new IllegalArgumentException("Email " + email + " уже занят");
        }

        final UserModel userModel = userDataService.getById(currentUserId);
        final EmailDataModel emailDataModel = new EmailDataModel();
        emailDataModel.setEmail(email);
        emailDataModel.setUserId(userModel.getId());

        emailDataService.save(emailDataModel);
        userCacheService.clearUserCache(currentUserId);
    }

    @Transactional
    public void deleteEmail(String email) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();
        final EmailDataModel emailDataModel = emailDataService.findByEmail(email);

        if (!emailDataModel.getUserId().equals(currentUserId)) {
            throw new SecurityException("Нельзя удалять email, который вам не принадлежит");
        }

        long totalEmailsCount = emailDataService.getTotalCountEmailsByUserId(currentUserId);
        if (totalEmailsCount <= 1) {
            throw new IllegalArgumentException("Нельзя удалить свой единственный email");
        }

        emailDataService.delete(emailDataModel);
        userCacheService.clearUserCache(currentUserId);
    }

    @Transactional
    public void addPhone(String phone) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();

        if (phoneDataService.existsByPhone(phone)) {
            throw new IllegalArgumentException("Phone " + phone + " уже занят");
        }

        final UserModel userModel = userDataService.getById(currentUserId);
        final PhoneDataModel phoneDataModel = new PhoneDataModel();

        phoneDataModel.setPhone(phone);
        phoneDataModel.setUserId(userModel.getId());
        phoneDataService.save(phoneDataModel);
        userCacheService.clearUserCache(currentUserId);
    }

    @Transactional
    public void deletePhone(String phone) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();
        final PhoneDataModel phoneDataModel = phoneDataService.getByPhone(phone);

        if (!phoneDataModel.getUserId().equals(currentUserId)) {
            throw new SecurityException("Нельзя удалять телефон, который вам не принадлежит");
        }

        long totalPhonesCount = phoneDataService.getTotalCountPhonesByUserId(currentUserId);
        if (totalPhonesCount <= 1) {
            throw new IllegalArgumentException("Нельзя удалить свой единственный телефон");
        }

        phoneDataService.delete(phoneDataModel);
        userCacheService.clearUserCache(currentUserId);
    }

    @Transactional
    public void updateEmail(String oldEmail, String newEmail) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();

        if (emailDataService.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Ошибка, Email: " + newEmail + " уже занят");
        }

        EmailDataModel emailDataModel = emailDataService.findByEmail(oldEmail);
        if (!emailDataModel.getUserId().equals(currentUserId)) {
            throw new SecurityException("Вы не можете обновить чужой email");
        }

        emailDataModel.setEmail(newEmail);
        emailDataService.save(emailDataModel);
        userCacheService.clearUserCache(currentUserId);
    }

    @Transactional
    public void updatePhone(String oldPhone, String newPhone) {
        final Long currentUserId = SecurityUtil.getCurrentUserId();

        if (phoneDataService.existsByPhone(newPhone)) {
            throw new IllegalArgumentException("Ошибка, Phone: " + newPhone + " уже занят");
        }

        PhoneDataModel phoneDataModel = phoneDataService.getByPhone(oldPhone);
        if (!phoneDataModel.getUserId().equals(currentUserId)) {
            throw new SecurityException("Вы не можете обновить чужой телефон");
        }

        phoneDataModel.setPhone(newPhone);
        phoneDataService.save(phoneDataModel);
        userCacheService.clearUserCache(currentUserId);
    }
}