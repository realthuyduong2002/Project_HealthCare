import express from "express";
import therapeutic from "../models/therapeutic.js";

import {
  getAllTherapeutic,
  addTherapeutic,
  getTherapeuticByID,
  updateTherapeutic,
} from "../controllers/managetherapeutic.js";

const router = express.Router();

router.get("/therapeutics", getAllTherapeutic);
router.get("/therapeutic/:id", getTherapeuticByID);
router.post("/therapeutic/addtherapeutic", addTherapeutic);
router.put("/therapeutic/updatetherapeutic/:id", updateTherapeutic);
export default router;
