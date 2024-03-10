import mongoose, { Schema } from "mongoose";

const { Schema } = mongoose;

const MedicineSchema = new mongoose.Schema({
  MedicineCost: {
    type: Number,
    require: true,
  },
  MedicineName: {
    type: String,
    require: true,
  },
  LocationProcedure: {
    type: String,
    require: true,
  },
  MedicineType: {
    type: String,
    require: true,
  },
});

export default mongoose.model("medicine", MedicineSchema);
