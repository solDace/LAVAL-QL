package org.example.clinique;

import org.example.visiblesymptom.type.VisibleSymptom;

import java.util.ArrayList;
import java.util.LinkedList;

public class Clinic {

    private ArrayList<String> listeMedecin;
    private ArrayList<String> listeRadiologie;

    public Clinic(/* ... */) {
        listeMedecin = new ArrayList<>();
        listeRadiologie = new ArrayList<>();
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
        return listeMedecin.get(0);
    }

    public String obtenirProchainPatientPourRadiologie() {
        return listeRadiologie.get(0);
    }

    // D'autres méthodes peuvent être nécessaires
}

