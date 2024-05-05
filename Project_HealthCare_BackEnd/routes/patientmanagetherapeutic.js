import express from "express";
import { 
  viewAllTherapeutics, 
  searchTherapeutics, 
} from "../controllers/patientmanagetherapeutic.js";

const router = express.Router();

router.get("/patient/therapeutics/:PatientID", viewAllTherapeutics);
router.get("/patient/therapeutics/search/:PatientID", searchTherapeutics);

export default router;
