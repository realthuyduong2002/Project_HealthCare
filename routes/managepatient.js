import express from "express";
import Patient from "../models/patient.js";

import {
  getAll,
  getPatient,
  addpatient,
} from "../controllers/managepatient.js";

const router = express.Router();
router.get("/", getAll);
router.get("/:id", getPatient);
router.post("/addpatient", addpatient);

export default router;
