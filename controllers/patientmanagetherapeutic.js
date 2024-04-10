import Therapeutic from "../models/therapeutic.js";
import Patient from "../models/patient.js";

// View all therapeutic
export const viewAllTherapeutics = async (req, res) => {
    try {
        const patientId = req.params.PatientID;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const therapeutics = await Therapeutic.find({ PatientID: patientId });
        return res.status(200).json(therapeutics);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};

// Search therapeutics
export const searchTherapeutics = async (req, res) => {
    try {
        const patientId = req.params.PatientID;
        const { keyword } = req.query;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const therapeutics = await Therapeutic.find({
            PatientID: patientId,
            Description: { $regex: keyword, $options: "i" },
        });
        return res.status(200).json(therapeutics);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};