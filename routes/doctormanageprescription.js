import express from "express";

import {
  getAllPrescription,
  getPrescriptionByID,
  addPrescription,
  updatePrescription,
  deletePrescription,
} from "../controllers/doctormanageprescription.js";

const router = express.Router();

router.get("/doctor/prescriptions", getAllPrescription);
router.get("/doctor/prescription/:id", getPrescriptionByID);
router.post("/doctor/prescription/addPrescription", addPrescription);
router.put("/doctor/prescription/updatePrescription/:id", updatePrescription);
router.delete(
  "/doctor/prescription/deletePrescription/:id",
  deletePrescription
);

export default router;
