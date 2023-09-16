package org.example.clinique;

import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;

import java.util.ArrayList;

public class Clinique {

    private final TriageType medecinTriageType;
    private final TriageType radiologieTriageType;
    private final ArrayList<String> listeMedecin;
    private final ArrayList<String> listeRadiologie;


    public Clinique(TriageType medecinTriageType, TriageType radiologieTriageType) {
        this.medecinTriageType = medecinTriageType;
        this.radiologieTriageType = radiologieTriageType;
        listeMedecin = new ArrayList<>();
        listeRadiologie = new ArrayList<>();
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        listeMedecin.add(name);

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
        if (listeMedecin.isEmpty())
            throw new CliniqueVideException("Aucun patient n'est attendu en consultation.");

        return listeMedecin.remove(0);
    }

    public String obtenirProchainPatientPourRadiologie() {
        if (listeRadiologie.isEmpty())
            throw new CliniqueVideException("Aucun patient n'est attendu en radiologie.");

        return listeRadiologie.remove(0);
    }

    public TriageType obtenirMedecinTriageType() {
        return medecinTriageType;
    }

    public TriageType obtenirRadiologieTriageType() {
        return radiologieTriageType;
    }

    public String obtenirDernierPatientFileRadiologie() {
        return listeRadiologie.get(listeRadiologie.size() - 1);
    }

    public String obtenirDernierPatientFileMedecin() {
        return listeMedecin.get(listeMedecin.size() - 1);
    }

    // D'autres méthodes peuvent être nécessaires
}

