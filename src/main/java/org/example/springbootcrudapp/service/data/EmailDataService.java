package org.example.springbootcrudapp.service.data;

import lombok.RequiredArgsConstructor;
import org.example.springbootcrudapp.entity.EmailData;
import org.example.springbootcrudapp.mapper.EmailDataMapper;
import org.example.springbootcrudapp.model.EmailDataModel;
import org.example.springbootcrudapp.repository.EmailDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDataService {
    private final EmailDataRepository emailDataRepository;

    private final EmailDataMapper emailDataMapper;

    public EmailDataModel findByEmail(final String email) {
        final EmailData entity = emailDataRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email " + email + " не найден"));

        return emailDataMapper.toEmailDataModel(entity);
    }

    public boolean existsByEmail(final String email) {
        return emailDataRepository.existsByEmail(email);
    }

    public EmailDataModel save(final EmailDataModel emailDataModel) {
        final EmailData entityToSave = emailDataMapper.toEmailDataEntity(emailDataModel);
        final EmailData saved = emailDataRepository.save(entityToSave);

        return emailDataMapper.toEmailDataModel(saved);
    }

    public void delete(final EmailDataModel emailDataModel) {
        emailDataRepository.delete(emailDataMapper.toEmailDataEntity(emailDataModel));
    }

    public long getTotalCountEmailsByUserId(Long userId) {
        return emailDataRepository.countByUserDataId(userId);
    }

}