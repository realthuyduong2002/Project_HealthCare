import HealthRecord from "../models/healthRecord.js";
import Patient from "../models/patient.js";

// View health record
export const viewHealthRecord = async (req, res) => {
    try {
        const patientId = req.params.PatientID;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const healthRecord = await HealthRecord.findOne({ PatientID: patientId });
        if (!healthRecord) {
            return res.status(404).json({ error: "Health record not found for this patient" });
        }
        return res.status(200).json(healthRecord);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};

// Search health record by ID
export const searchHealthRecordByID = async (req, res) => {
    try {
        const patientId = req.params.PatientID;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const healthRecord = await HealthRecord.findOne({ PatientID: patientId });
        if (!healthRecord) {
            return res.status(404).json({ error: "Health record not found for this patient" });
        }
        return res.status(200).json(healthRecord);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};
