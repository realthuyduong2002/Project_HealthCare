import HealthInsurance from "../models/healthInsurance.js";
import Patient from "../models/patient.js";

// View health insurance
export const viewHealthInsurance = async (req, res) => {
    try {
        const patientId = req.params.PatientID;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const healthInsurance = await HealthInsurance.findOne({ PatietnID: patientId });
        if (!healthInsurance) {
            return res.status(404).json({ error: "Health insurance not found for this patient" });
        }
        return res.status(200).json(healthInsurance);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};