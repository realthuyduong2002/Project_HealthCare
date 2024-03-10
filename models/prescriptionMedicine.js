import mongoose from "mongoose";

const { Schema } = mongoose;

const PrescriptionMedicineSchema = new mongoose.Schema({
  PrescriptionID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "prescription",
    },
  ],
  MedicineID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "medicine",
    },
  ],
});

export default mongoose.model(
  "prescriptionMedicine",
  PrescriptionMedicineSchema
);
