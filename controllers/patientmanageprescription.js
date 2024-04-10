import Prescription from "../models/prescriptions.js";
import Patient from "../models/patient.js";

// View all prescriptions
export const viewAllPrescriptions = async (req, res, next) => {
    try {
        const patientId = req.params.PatientID;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const prescriptions = await Prescription.find({ PatientID: patientId });
        return res.status(200).json(prescriptions);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};

// View prescription by ID
export const viewPrescriptionByID = async (req, res, next) => {
    try {
        const patientId = req.params.PatientID;
        const prescriptionId = req.params.id;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const prescription = await Prescription.findOne({
            PatientID: patientId,
            _id: prescriptionId,
        });
        if (!prescription) {
            return res
                .status(404)
                .json({ error: "Prescription not found for this patient" });
        }
        return res.status(200).json(prescription);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};

// Search prescriptions
export const searchPrescriptions = async (req, res, next) => {
    try {
        const patientId = req.params.PatientID;
        const { keyword } = req.query;
        const existingPatient = await Patient.findById(patientId);
        if (!existingPatient) {
            return res.status(404).json({ error: "Patient not found" });
        }
        const prescriptions = await Prescription.find({
            PatientID: patientId,
            Description: { $regex: keyword, $options: "i" },
        });
        return res.status(200).json(prescriptions);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal server error" });
    }
};