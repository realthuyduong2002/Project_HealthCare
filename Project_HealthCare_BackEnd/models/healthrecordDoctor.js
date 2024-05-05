import mongoose from "mongoose";

const { Schema } = mongoose;

const HealthrecordDoctorScbema = new mongoose.Schema({
  RecordID: {
    type: Number,
    ref: "healthRecord",
  },
  DoctorID: {
    type: Number,
    ref: "doctor",
  },
});

export default mongoose.model("healthrecordDoctor", HealthrecordDoctorScbema);
