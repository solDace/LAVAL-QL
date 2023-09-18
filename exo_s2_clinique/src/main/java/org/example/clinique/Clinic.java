package org.example.clinique;

import org.example.triage.type.TriageType;
import org.example.visiblesymptom.type.VisibleSymptom;

import java.util.ArrayList;

public class Clinic {

    private final ArrayList<Patient> listeMedecin;
    private final ArrayList<Patient> listeRadiologie;
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
            var index = obtenirPositionPremierPatientPrioritaireDeLaListeMedecin(5);
            listeMedecin.add(index, new Patient(name, gravity));

            if (visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN) {
                index = obtenirPositionPremierPatientPrioritaireDeLaListeRadiologie(5);
                listeRadiologie.add(new Patient(name, gravity));
            }

        } else {
            listeMedecin.add(new Patient(name, gravity));

            if (visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN) {
                listeRadiologie.add(new Patient(name, gravity));
            }
        }
    }

    public boolean listeMedecinEstVide() {
        return listeMedecin.isEmpty();
    }

    public boolean listeRadiologieEstVide() {
        return listeRadiologie.isEmpty();
    }

    public Patient obtenirProchainPatientPourMedecin() {
        try {
            return listeMedecin.remove(0);
        } catch (IndexOutOfBoundsException error) {
            return null;
        }
    }

    public Patient obtenirProchainPatientPourRadiologie() {
        try {
            return listeRadiologie.remove(0);
        } catch (IndexOutOfBoundsException error) {
            return null;
        }
    }

    private int obtenirPositionPremierPatientPrioritaireDeLaListeMedecin(int gravitePrioritaire) {
        int index = 0;
        for (Patient patient : listeMedecin) {
            if (patient.getGravity() >= gravitePrioritaire) {
                return index + 1;
            }
            index++;
        }
        return 0;
    }

    private int obtenirPositionPremierPatientPrioritaireDeLaListeRadiologie(int gravitePrioritaire) {
        int index = 0;
        for (Patient patient : listeRadiologie) {
            if (patient.getGravity() >= gravitePrioritaire) {
                return index + 1;
            }
            index++;
        }
        return 0;
    }

    // D'autres méthodes peuvent être nécessaires
}

