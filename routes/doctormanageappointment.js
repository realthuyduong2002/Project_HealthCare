import express from "express";
import {
  getAllAppointment,
  getAppointmentByID,
} from "../controllers/doctormanageappointment.js";
const router = express.Router();

router.get("/doctor/appointments", getAllAppointment);
router.get("/doctor/appointment/:id", getAppointmentByID);

export default router;
