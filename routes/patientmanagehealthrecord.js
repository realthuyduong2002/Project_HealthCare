import express from "express";
import { 
    viewHealthRecord, 
    searchHealthRecordByID 
} from "../controllers/patientmanagehealthrecord.js";

const router = express.Router();

router.get('/patient/healthRecord/:PatientID', viewHealthRecord);
router.get('/patient/healthRecord/search/:PatientID', searchHealthRecordByID);

export default router;