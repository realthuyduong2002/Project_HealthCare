import express from "express";
import medicine from "../models/medicine.js";

import {
  getAllMedicines,
  getMedicineByID,
  addMedicine,
  updateMedicine,
  deleteMedicine,
  searchMedicine,
} from "../controllers/managemedicine.js";

const router = express.Router();

router.get("/medicines", getAllMedicines);
router.get("/medicine/:id", getMedicineByID);
router.post("/medicine/addmedicine", addMedicine);
router.put("/medicine/updatemedicine/:id", updateMedicine);
router.delete("/medicine/deletemedicine/:id", deleteMedicine);
router.get("/medicine/search/:keyword", searchMedicine);

export default router;