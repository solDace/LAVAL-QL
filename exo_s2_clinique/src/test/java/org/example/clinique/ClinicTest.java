package org.example.clinique;

import org.example.visiblesymptom.type.VisibleSymptom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ClinicTest {

    private Clinic clinic;

    @BeforeEach
    void setUp() {
        clinic = new Clinic();
    }

    @Test
    public void etantDonneUneCliniqueVide_quandObtenirProchainPatientMedecin_alorsRetourneNull() {

        assertNull(clinic.obtenirProchainPatientPourMedecin());
    }

    @Test
    public void etantDonneUneCliniqueVide_quandObtenirProchainPatientRadiologie_alorsRetourneNull() {

        assertNull(clinic.obtenirProchainPatientPourRadiologie());
    }

    @Test
    public void etantDonneCliniqueVide_quandPatientArrive_alorsFileMedecinPlusVide() {

        clinic.triagePatient("Bob", 1, VisibleSymptom.MIGRAINE);

        assertFalse(clinic.listeMedecinEstVide());
    }


    @Test
    public void etantDonneCliniqueVide_quandPatientArriveAvecBrokenBone_devraitAjouterPatientAFileRadiologie() {

        clinic.triagePatient("Bob", 5, VisibleSymptom.BROKEN_BONE);

        assertFalse(clinic.listeRadiologieEstVide());
    }

    @Test
    public void etantDonneCliniqueVide_quandPatientArriveAvecSprain_devraitAjouterPatientAFileRadiologie() {

        clinic.triagePatient("Bob", 5, VisibleSymptom.SPRAIN);

        assertFalse(clinic.listeRadiologieEstVide());
    }

    @Test
    public void etantDonneCliniqueVide_quandPatientArrive_ilEstLePremierDansLaFileMedecin() {

        clinic.triagePatient("Bob", 1, VisibleSymptom.BROKEN_BONE);

        String patientName = clinic.obtenirProchainPatientPourMedecin();

        assertEquals("Bob", patientName);
    }

    @Test
    public void quandArrivePatientsAvecMemesConditions_alorsPatientsRetournerSelonOrdreArrivee() {

        quandArrivePatientsAvecMemesConditions();

        String premierPatient = clinic.obtenirProchainPatientPourMedecin();
        String deuxiemePatient = clinic.obtenirProchainPatientPourMedecin();

        assertEquals("Jean", premierPatient);
        assertEquals("Julie", deuxiemePatient);
    }

    private void quandArrivePatientsAvecMemesConditions() {
        clinic.triagePatient("Jean", 1, VisibleSymptom.COLD);
        clinic.triagePatient("Julie", 1, VisibleSymptom.COLD);
    }

}