import express from "express";
import {
    viewAllPrescriptions,
    viewPrescriptionByID,
    searchPrescriptions
} from "../controllers/patientmanageprescription.js";

const router = express.Router();

router.get("/patient/prescriptions/:PatientID", viewAllPrescriptions);
router.get("/patient/prescriptions/:PatientID/:id", viewPrescriptionByID);
router.get("/patient/prescriptions/search/:PatientID", searchPrescriptions);

export default router;