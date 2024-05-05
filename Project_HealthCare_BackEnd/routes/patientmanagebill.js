import express from "express";
import {
  addBill,
  getAllBill,
  getBillByID,
} from "../controllers/patientmanagebill.js";

const router = express.Router();

router.get("/patient/bills/:PatientID", getAllBill);
router.get("/patient/bill/:PatientID/:id", getBillByID);
router.post("/patient/bill/addBill", addBill);

export default router;
