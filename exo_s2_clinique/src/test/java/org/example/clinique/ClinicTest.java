package org.example.clinique;

import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ClinicTest {

    private Clinic clinic;

    @BeforeEach
    void setUp() {
        TriageType medecinTriageType = TriageType.FIFO;
        TriageType radiologieTriageType = TriageType.FIFO;
        clinic = new Clinic(medecinTriageType, radiologieTriageType);
    }

    @Test
    public void quandInstanciation_devraitRetournerTriageType() {

        assertEquals(TriageType.FIFO, clinic.obtenirMedecinTriageType());
        assertEquals(TriageType.FIFO, clinic.obtenirRadiologieTriageType());
    }

    @Test
    public void quandInstanciation_devraitEtreVide() {

        assertTrue(clinic.listeMedecinEstVide());
        assertTrue(clinic.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandObtenirProchainPatient_devraitRetournerException() {

        IndexOutOfBoundsException exceptionMedecin = assertThrows(IndexOutOfBoundsException.class, () -> clinic.obtenirProchainPatientPourMedecin());
        IndexOutOfBoundsException exceptionRadiologie = assertThrows(IndexOutOfBoundsException.class, () -> clinic.obtenirProchainPatientPourRadiologie());

        assertEquals("Aucun patient n'est attendu en consultation.", exceptionMedecin.getMessage());
        assertEquals("Aucun patient n'est attendu en radiologie.", exceptionRadiologie.getMessage());
    }

    @Test
    public void cliniqueVide_quandPatientArrive_devraitRemplirFileMedecin() {

        clinic.triagePatient("Bob", 1, VisibleSymptom.MIGRAINE);

        assertFalse(clinic.listeMedecinEstVide());
    }

    @Test
    public void cliniqueVide_quandPatientArriveAvecSymptomesRadiologie_devraitEtreAjouteAFileRadiologie() {

        clinic.triagePatient("Bob", 5, VisibleSymptom.BROKEN_BONE);

        assertFalse(clinic.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandPatientArrive_devraitEtrePremierServi() {

        clinic.triagePatient("Bob", 1, VisibleSymptom.BROKEN_BONE);

        String patientNameMedecin = clinic.obtenirProchainPatientPourMedecin();
        String patientNameRadiologie = clinic.obtenirProchainPatientPourRadiologie();

        assertEquals("Bob", patientNameMedecin);
        assertEquals("Bob", patientNameRadiologie);
    }

    @Test
    public void clinique_quandPlusieursPatientsArrivent_devraientEtreServisDansLOrdre(){

        clinic.triagePatient("Jean", 1, VisibleSymptom.BROKEN_BONE);
        clinic.triagePatient("Julie", 1, VisibleSymptom.BROKEN_BONE);

        String premierPatientMedecin = clinic.obtenirProchainPatientPourMedecin();
        String deuxiemePatientMedecin = clinic.obtenirProchainPatientPourMedecin();

        String premierPatientRadiologie = clinic.obtenirProchainPatientPourRadiologie();
        String deuxiemePatientRadiologie = clinic.obtenirProchainPatientPourRadiologie();

        assertEquals("Jean", premierPatientMedecin);
        assertEquals("Julie", deuxiemePatientMedecin);

        assertEquals("Jean", premierPatientRadiologie);
        assertEquals("Julie", deuxiemePatientRadiologie);
    }

}