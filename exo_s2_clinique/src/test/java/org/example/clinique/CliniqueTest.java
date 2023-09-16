package org.example.clinique;


import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CliniqueTest {

    private Clinique clinique;

    @BeforeEach
    void setUp() {
        TriageType medecinTriageType = TriageType.FIFO;
        TriageType radiologieTriageType = TriageType.FIFO;
        clinique = new Clinique(medecinTriageType, radiologieTriageType);
    }

    private void exceptionListeMedecinVide() {
        clinique.obtenirProchainPatientPourMedecin();
    }

    private void exceptionListeRadiologieVide() {
        clinique.obtenirProchainPatientPourRadiologie();
    }

    private void unPatientDansFileMedecin(String name, int gravity, VisibleSymptom visibleSymptom) {
        clinique.triagePatient(name, gravity, visibleSymptom);
    }

    @Test
    public void quandInstanciation_devraitRetournerTriageType() {

        assertEquals(TriageType.FIFO, clinique.obtenirMedecinTriageType());
        assertEquals(TriageType.FIFO, clinique.obtenirRadiologieTriageType());
    }

    @Test
    public void quandInstanciation_devraitEtreVide() {

        assertTrue(clinique.listeMedecinEstVide());
        assertTrue(clinique.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandObtenirProchainPatient_devraitRetournerException() {

        ThrowingCallable exceptionListeMedecinVide = this::exceptionListeMedecinVide;
        ThrowingCallable exceptionListeRadiologieVide = this::exceptionListeRadiologieVide;

        assertThatThrownBy(exceptionListeMedecinVide).isInstanceOf(CliniqueVideException.class).hasMessage("Aucun patient n'est attendu en consultation.");
        assertThatThrownBy(exceptionListeRadiologieVide).isInstanceOf(CliniqueVideException.class).hasMessage("Aucun patient n'est attendu en radiologie.");

    }

    @Test
    public void cliniqueVide_quandPatientArriveAvecMigraine_devraitRemplirFileMedecinUniquement() {

        clinique.triagePatient("Bob", 1, VisibleSymptom.MIGRAINE);

        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientPourMedecin());
        assertTrue(clinique.listeRadiologieEstVide());
    }

    @Test
    public void cliniqueVide_quandPatientArriveAvecSymptomesRadiologie_devraitEtreAjouteAFileMedecinEtRadiologie() {

        clinique.triagePatient("Bob", 5, VisibleSymptom.BROKEN_BONE);

        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientPourMedecin());
        assertThat("Bob").isEqualTo(clinique.obtenirProchainPatientPourRadiologie());
    }

    @Test
    public void unPatientFileMedecinEtRadiologie_quandUnPatientArriveAvecFLU_devraitEtreDeuxiemeDansFileMedecinUniquement() {

        unPatientDansFileMedecin("Jean", 1, VisibleSymptom.BROKEN_BONE);

        clinique.triagePatient("Julie", 1, VisibleSymptom.FLU);

        assertThat("Julie").isEqualTo(clinique.obtenirDernierPatientFileMedecin());
        assertThat("Julie").isNotEqualTo(clinique.obtenirDernierPatientFileRadiologie());
    }


}