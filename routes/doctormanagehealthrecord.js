import express from "express";

import {
  getAllHealthRecord,
  getHealthRecordById,
  addHealthRecord,
  updateHealthRecord,
  deleteHealthRecord,
} from "../controllers/doctormanagehealthrecord.js";

const router = express.Router();

router.get("/doctor/healthRecords", getAllHealthRecord);
router.get("/doctor/healthRecord/:id", getHealthRecordById);
router.post("/doctor/healthRecord/addHealthRecord", addHealthRecord);
router.put("/doctor/healthRecord/updateHealthRecord/:id", updateHealthRecord);
router.delete(
  "/doctor/healthRecord/deleteHealthRecord/:id",
  deleteHealthRecord
);

export default router;
