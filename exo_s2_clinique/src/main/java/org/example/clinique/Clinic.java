package org.example.clinique;

import org.example.visiblesymptom.type.VisibleSymptom;

import java.util.ArrayList;
import java.util.LinkedList;

public class Clinic {

    private LinkedList<String> listeMedecin;
    private LinkedList<String> listeRadiologie;

    public Clinic(/* ... */) {
        listeMedecin = new LinkedList<>();
        listeRadiologie = new LinkedList<>();
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        listeMedecin.add(name);

        if(visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN) {
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
        if (listeMedecin.isEmpty()) {
            return null;
        }

        return listeMedecin.pop();
    }

    public String obtenirProchainPatientPourRadiologie() {
        if (listeRadiologie.isEmpty()) {
            return null;
        }

        return listeRadiologie.pop();
    }

    // D'autres méthodes peuvent être nécessaires
}

