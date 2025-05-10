package org.example.springbootcrudapp.service.data;

import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.entity.PhoneData;
import org.example.springbootcrudapp.mapper.PhoneDataMapper;
import org.example.springbootcrudapp.model.PhoneDataModel;
import org.example.springbootcrudapp.repository.PhoneDataRepository;
import org.example.springbootcrudapp.service.cache.UserCacheService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneDataService {
    private final PhoneDataRepository phoneDataRepository;
    private final PhoneDataMapper phoneDataMapper;

    public PhoneDataModel getByPhone(final String phone) {
        final PhoneData phoneData = phoneDataRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Phone " + phone + " не найден"));

        return phoneDataMapper.toPhoneDataModel(phoneData);
    }

    public boolean existsByPhone(final String phone) {
        return phoneDataRepository.existsByPhone(phone);
    }

    public PhoneDataModel save(final PhoneDataModel phoneData) {
        final PhoneData phoneDataToSave = phoneDataMapper.toPhoneDataEntity(phoneData);
        final PhoneData saved = phoneDataRepository.save(phoneDataToSave);

        return phoneDataMapper.toPhoneDataModel(saved);
    }

    public void delete(final PhoneDataModel phoneData) {
        phoneDataRepository.delete(phoneDataMapper.toPhoneDataEntity(phoneData));
    }

    public long getTotalCountPhonesByUserId(Long userId) {
        return phoneDataRepository.countByUserDataId(userId);
    }
}