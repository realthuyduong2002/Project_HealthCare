import express from "express";
import doctor from "../models/doctor.js";

import {
  getAllDoctors,
  addDoctor,
  getDoctorByID,
  updateDoctor,
  deleteDoctor,
  searchDoctor,
  findBySpecialty,
} from "../controllers/managedoctor.js";

const router = express.Router();

router.get("/doctors", getAllDoctors);
router.get("/doctor/:id", getDoctorByID);
router.post("/doctor/adddoctor", addDoctor);
router.put("/doctor/updatedoctor/:id", updateDoctor);
router.delete("/doctor/deletedoctor/:id", deleteDoctor);
router.get("/doctor/search/:DoctorName", searchDoctor);
router.get("/findBySpecialty/:Speciality", findBySpecialty);

export default router;
