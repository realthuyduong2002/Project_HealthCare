import express from "express";
import therapeutic from "../models/therapeutic.js";

import {
  getAllTherapeutic,
  addTherapeutic,
  getTherapeuticByID,
  updateTherapeutic,
} from "../controllers/doctormanagetherapeutic.js";

const router = express.Router();

router.get("/doctor/therapeutics", getAllTherapeutic);
router.get("/doctor/therapeutic/:id", getTherapeuticByID);
router.post("/doctor/therapeutic/addtherapeutic", addTherapeutic);
router.put("/doctor/therapeutic/updatetherapeutic/:id", updateTherapeutic);

export default router;
