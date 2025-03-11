package service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MedicalServiceImplTest3 {
    @Test
    void checkBloodPressureWithNoAlertSendTest() {
        // Given
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);

        PatientInfo patientInfo = new PatientInfo(
                "1",
                "Иван",
                "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("35"), new BloodPressure(120, 80))
        );

        when(patientInfoFileRepository.getById("1"))
                .thenReturn(patientInfo);

        // When
        medicalService.checkBloodPressure("1", new BloodPressure(120, 80));

        // Then
        verify(alertService, Mockito.never()).send(Mockito.anyString());
    }
}
