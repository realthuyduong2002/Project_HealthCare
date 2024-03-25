import express from "express";
import {
  addPatient,
  getAllPatient,
  getPatientById,
  updatePatientById,
} from "../controllers/doctormanagepatient.js";

const router = express.Router();

router.get("/doctor/patients", getAllPatient);
router.get("/doctor/patient/:id", getPatientById);
router.post("/doctor/patient/addPatient", addPatient);
router.put("/doctor/patient/updatePatient/:id", updatePatientById);

export default router;
