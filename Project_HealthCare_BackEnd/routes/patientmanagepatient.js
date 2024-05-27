import express from "express";
import {
  updatePatient,
  deletePatient,
  viewPatient,
  getAllPatientByPhoneNumber,
} from "../controllers/patientmanagepatient.js";

const router = express.Router();

router.put("/patient/:PatientID", updatePatient);
router.delete("/patient/deletePatient/:PatientID", deletePatient);
router.get("/patient/:PatientID", viewPatient);
/**
 * router.get(
  "/getAllPatientByPhoneNumber/:phoneNumber",
  getAllPatientByPhoneNumber
);
 */
export default router;
