package org.example;

import org.example.triage.type.TriageType;



public class Main {
    public static void main(String[] args) {
        // Ceci n'est pas un test!! C'est un exemple d'utilisation.
        TriageType doctorTriageType = TriageType.FIFO;
        TriageType radiologyTriageType = TriageType.FIFO;

        // Clinic clinic = new Clinic(doctorTriageType, radiologyTriageType /* , ... */)
        // clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
    }
}