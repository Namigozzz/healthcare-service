package service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MedicalServiceImplTest2 {
    @Test
    void checkTemperatureTest() {
        // Given
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, alertService);

        PatientInfo patientInfo = new PatientInfo(
                "1",
                "Иван",
                "Петров",
                LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80))
        );

        when(patientInfoFileRepository.getById("1"))
                .thenReturn(patientInfo);

        // When
        BigDecimal highTemperature = new BigDecimal("38.2");  // Увеличиваем температуру
        medicalService.checkTemperature("1", highTemperature);

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(alertService).send(messageCaptor.capture());
        assertEquals("Warning, patient with id: 1, need help", messageCaptor.getValue());
    }
}
