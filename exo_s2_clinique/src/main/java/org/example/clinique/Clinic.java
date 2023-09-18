package org.example.clinique;

import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;

import java.util.ArrayList;

public class Clinic {

    private final ArrayList<String> listeMedecin;
    private final ArrayList<String> listeRadiologie;
    private TriageType triage;

    public void setTriage(TriageType triage) {
        this.triage = triage;
    }

    public Clinic(TriageType triage) {
        this.triage = triage;
        listeMedecin = new ArrayList<>();
        listeRadiologie = new ArrayList<>();
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {

        if ((triage == TriageType.GRAVITY) && gravity >= 5) {
            listeMedecin.add(0, name);
        } else {
            listeMedecin.add(name);
        }

        if (visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN) {
            listeRadiologie.add(name);
        }
    }

    public boolean listeMedecinEstVide() {
        return listeMedecin.isEmpty();
    }

    public boolean listeRadiologieEstVide() {
        return listeRadiologie.isEmpty();
    }

    public String obtenirProchainPatientPourMedecin() {
        try {
            return listeMedecin.remove(0);
        } catch (IndexOutOfBoundsException error) {
            return null;
        }
    }

    public String obtenirProchainPatientPourRadiologie() {
        try {
            return listeRadiologie.remove(0);
        } catch (IndexOutOfBoundsException error) {
            return null;
        }
    }

    // D'autres méthodes peuvent être nécessaires
}

