import express from "express";
import {
  getAllAppointment,
  getAppointmentByID,
  addAppointment,
  getLatestAppointment,
} from "../controllers/patientmanageappointment.js";

const router = express.Router();

router.get("/patient/appointments/:PatientID", getAllAppointment);
router.get("/patient/appointment/:PatientID/:id", getAppointmentByID);
router.post("/patient/appointment/addAppointment", addAppointment);
router.get("/latest/:PatientID", getLatestAppointment);

export default router;
