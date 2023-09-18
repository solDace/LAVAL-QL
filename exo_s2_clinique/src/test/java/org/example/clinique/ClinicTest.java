package org.example.clinique;

import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ClinicTest {

    @BeforeEach
    void setUpEmptyFIFOClinic() {
        clinic = new Clinic(TriageType.FIFO);
    }

    @Test
    public void quandCliniqueCree_alorsToutesLesListesSontVides() {

        assertTrue(clinic.listeMedecinEstVide());
        assertTrue(clinic.listeRadiologieEstVide());
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
    public void etantDonneCliniqueVide_quandPatientArrive_alorsFileMedecinNestPlusVide() {

        clinic.triagePatient(PATIENT_ALICE, 1, VisibleSymptom.MIGRAINE);

        assertFalse(clinic.listeMedecinEstVide());
    }


    @Test
    public void quandPatientArriveAvecOsBrise_alorsFileRadiologieNestPlusVide() {

        clinic.triagePatient(PATIENT_ALICE, 5, VisibleSymptom.BROKEN_BONE);

        assertFalse(clinic.listeRadiologieEstVide());
    }

    @Test
    public void quandPatientArriveAvecEntorse_alorsFileRadiologieNestPlusVide() {

        clinic.triagePatient(PATIENT_ALICE, 5, VisibleSymptom.SPRAIN);

        assertFalse(clinic.listeRadiologieEstVide());
    }

    @Test
    public void etantDonneCliniqueVide_quandPatientArrive_ilEstLePremierDansLaFileMedecin() {

        clinic.triagePatient(PATIENT_ALICE, 1, VisibleSymptom.BROKEN_BONE);

        String patientName = clinic.obtenirProchainPatientPourMedecin();

        assertEquals(PATIENT_ALICE, patientName);
    }

    @Test
    public void quandArrivePatientsAvecMemesConditions_alorsPatientsRetournerSelonOrdreArrivee() {

        clinic.triagePatient(PATIENT_ALICE, 1, VisibleSymptom.COLD);
        clinic.triagePatient(PATIENT_BOB, 1, VisibleSymptom.COLD);

        String premierPatient = clinic.obtenirProchainPatientPourMedecin();
        String deuxiemePatient = clinic.obtenirProchainPatientPourMedecin();

        assertEquals(PATIENT_ALICE, premierPatient);
        assertEquals(PATIENT_BOB, deuxiemePatient);
    }

    @Test
    public void etantDonneCliniqueTriantParGravite_quandArrivePatientAvecGraviteDAuMoinsCinq_alorsPatientPlacerEnTeteDeFile() {

        clinic.setTriage(TriageType.GRAVITY);
        clinic.triagePatient(PATIENT_ALICE, 1, VisibleSymptom.COLD);
        clinic.triagePatient(PATIENT_BOB, 5, VisibleSymptom.COLD);

        String premierPatient = clinic.obtenirProchainPatientPourMedecin();
        String deuxiemePatient = clinic.obtenirProchainPatientPourMedecin();

        assertEquals(PATIENT_BOB, premierPatient);
        assertEquals(PATIENT_ALICE, deuxiemePatient);
    }

    private Clinic clinic;
    private static final String PATIENT_ALICE = "Alice";
    private static final String PATIENT_BOB = "Bob";

}