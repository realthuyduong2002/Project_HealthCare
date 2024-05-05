import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);
const { Schema } = mongoose;

const HealthInsuranceSchema = new mongoose.Schema({
  _id: Number,
  PatietnID: {
    type: Number,
    ref: "patient",
  },
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

HealthInsuranceSchema.plugin(AutoIncrement, {
  id: "healthinsurance_seq",
  inc_field: "_id",
});

export default mongoose.model("healthInsurance", HealthInsuranceSchema);
