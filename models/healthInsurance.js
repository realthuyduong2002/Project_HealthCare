import mongoose from "mongoose";

const { Schema } = mongoose;

const HealthInsuranceSchema = new mongoose.Schema({
  PatietnID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "patient",
    },
  ],
  Hospital: {
    type: String,
    require: true,
  },
  ExpiryDate: {
    type: Date,
    require: true,
  },
  ValidDate: {
    type: Date,
    require: true,
  },
  InsuranceName: {
    type: String,
    require: true,
  },
});

export default mongoose.model("healthInsurance", HealthInsuranceSchema);
