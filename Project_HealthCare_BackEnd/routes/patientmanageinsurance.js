import express from "express";
import { viewHealthInsurance } from "../controllers/patientmanageinsurance.js";

const router = express.Router();

router.get("/patient/insurance/:PatientID", viewHealthInsurance);

export default router;