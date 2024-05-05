import mongoose from "mongoose";

const { Schema } = mongoose;

const PrescriptionMedicineSchema = new mongoose.Schema({
  PrescriptionID: {
    type: Number,
    ref: "prescription",
  },
  MedicineID: {
    type: Number,
    ref: "medicine",
  },
});

export default mongoose.model(
  "prescriptionMedicine",
  PrescriptionMedicineSchema
);
