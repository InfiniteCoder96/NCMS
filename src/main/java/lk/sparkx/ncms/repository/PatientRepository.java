package lk.sparkx.ncms.repository;

import lk.sparkx.ncms.model.Patient;

public interface PatientRepository{
    public boolean registerPatient(Patient patient);
    public String generatePatientSerialNo();
    Patient getPatient(String patientIdOrSerialNo);
}
