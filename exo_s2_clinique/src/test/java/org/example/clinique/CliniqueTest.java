package org.example.clinique;


import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;
import org.junit.Test;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CliniqueTest {

    private Clinique clinique;

    private void setUp() {
        clinique = new Clinique(TriageType.FIFO, TriageType.FIFO);
    }

    private void setUp(TriageType medecinTriageType, TriageType radiologieTriageType) {
        clinique = new Clinique(medecinTriageType, radiologieTriageType);
    }

    private void exceptionListeMedecinVide() {
        clinique.obtenirProchainPatientFileMedecin();
    }

    private void exceptionListeRadiologieVide() {
        clinique.obtenirProchainPatientPourRadiologie();
    }

    private void unPatientDansFileMedecin(String name, int gravity, VisibleSymptom visibleSymptom) {
        clinique.triagePatient(name, gravity, visibleSymptom);
    }

    private void unPatientDansFileMedecinEtRadiologie(String name, int gravity, VisibleSymptom visibleSymptom) {
        clinique.triagePatient(name, gravity, visibleSymptom);
    }

    @Test
    public void quandInstanciation_devraitRetournerTriageType() {
        setUp();

        assertEquals(TriageType.FIFO, clinique.obtenirMedecinTriageType());
        assertEquals(TriageType.FIFO, clinique.obtenirRadiologieTriageType());
    }

    @Test
    public void quandInstanciation_devraitEtreVide() {
        setUp();

        assertTrue(clinique.listeMedecinEstVide());
        assertTrue(clinique.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandObtenirProchainPatient_devraitRetournerException() {
        setUp();

        ThrowingCallable exceptionListeMedecinVide = this::exceptionListeMedecinVide;
        ThrowingCallable exceptionListeRadiologieVide = this::exceptionListeRadiologieVide;

        assertThatThrownBy(exceptionListeMedecinVide).isInstanceOf(CliniqueVideException.class).hasMessage("Aucun patient n'est attendu en consultation.");
        assertThatThrownBy(exceptionListeRadiologieVide).isInstanceOf(CliniqueVideException.class).hasMessage("Aucun patient n'est attendu en radiologie.");

    }

    @Test
    public void cliniqueVide_quandPatientArriveAvecMigraine_devraitRemplirFileMedecinUniquement() {
        setUp();

        clinique.triagePatient("Bob", 1, VisibleSymptom.MIGRAINE);

        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientFileMedecin());
        assertTrue(clinique.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandPatientArriveAvecSymptomesRadiologie_devraitEtreAjouteAFileMedecinEtRadiologie() {
        setUp();

        clinique.triagePatient("Bob", 5, VisibleSymptom.BROKEN_BONE);

        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientFileMedecin());
        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientPourRadiologie());
    }

    @Test
    public void unPatientFileMedecinFIFO_quandUnPatientArriveAvecFLU_devraitEtreDeuxiemeDansFileMedecinUniquement() {
        setUp();
        unPatientDansFileMedecin("Jean", 1, VisibleSymptom.BROKEN_BONE);

        clinique.triagePatient("Julie", 1, VisibleSymptom.FLU);

        assertThat("Julie").isEqualTo(clinique.obtenirDernierPatientFileMedecin());
        assertThat("Julie").isNotEqualTo(clinique.obtenirDernierPatientFileRadiologie());
    }

    @Test
    public void unPatientFileMedecinGRAVITY_quandPatientArriveAvecFLUGravity7_devraitEtrePremierServi() {
        setUp(TriageType.GRAVITY, TriageType.FIFO);
        unPatientDansFileMedecin("Paul", 1, VisibleSymptom.COLD);

        clinique.triagePatient("Clara", 7, VisibleSymptom.FLU);

        assertThat("Clara").isEqualTo(clinique.obtenirProchainPatientFileMedecin());
        assertThat("Paul").isEqualTo(clinique.obtenirProchainPatientFileMedecin());
    }

    @Test
    public void unPatientFileMedecinGRAVITYetRadiologieFIFO_quandPatientArriveAvecBROKEN_BONEGravity7_devraitEtrePremierServi() {
        setUp(TriageType.GRAVITY, TriageType.FIFO);
        unPatientDansFileMedecinEtRadiologie("Marie", 4, VisibleSymptom.SPRAIN);

        clinique.triagePatient("Jacques", 7, VisibleSymptom.BROKEN_BONE);

        assertThat("Jacques").isEqualTo(clinique.obtenirDernierPatientFileRadiologie());
    }


}