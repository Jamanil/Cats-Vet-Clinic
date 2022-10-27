package ru.jamanil.catVetClinicDb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.MedicalHistory;
import ru.jamanil.catVetClinicDb.repositories.MedicalHistoryRepository;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Service
@Transactional(readOnly = true)
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public List<MedicalHistory> findAllByCat(Cat cat) {
        return medicalHistoryRepository.findAllByCat(cat);
    }

    public Optional<MedicalHistory> findById(int id) {
        return medicalHistoryRepository.findById(id);
    }

    @Transactional
    public void save(MedicalHistory medicalHistory) {
        String staffName = AuthenticationGetter.getStaffName();
        Date date = new Date();
        medicalHistory.setDate(date);
        medicalHistory.setCreatedBy(staffName);
        medicalHistory.setUpdatedAt(date);
        medicalHistory.setUpdatedBy(staffName);
        medicalHistoryRepository.save(medicalHistory);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public void update(MedicalHistory updatedHistory, int id) {
        Optional<MedicalHistory> optionalHistory = findById(id);

        if(optionalHistory.isPresent()) {
            MedicalHistory history = optionalHistory.get();

            history.setSymptoms(updatedHistory.getSymptoms());
            history.setDiagnosis(updatedHistory.getDiagnosis());
            history.setTherapy(updatedHistory.getTherapy());
            history.setOutcome(updatedHistory.getOutcome());
            history.setUpdatedAt(new Date());
            history.setUpdatedBy(AuthenticationGetter.getStaffName());

            medicalHistoryRepository.save(history);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        medicalHistoryRepository.deleteById(id);
    }
}
