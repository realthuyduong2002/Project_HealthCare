import express from "express";
import { 
  updatePatient, 
  deletePatient,
  viewPatient
} from "../controllers/patientmanagepatient.js";

const router = express.Router();

router.put("/patient/:PatientID", updatePatient);
router.delete("/patient/deletePatient/:PatientID", deletePatient);
router.get("/patient/:PatientID", viewPatient);

export default router;