import mongoose from "mongoose";

const { Schema } = mongoose;

const HealthrecordDoctorScbema = new mongoose.Schema({
  RecordID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "healthRecord",
    },
  ],
  DoctorID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "doctor",
    },
  ],
});

export default mongoose.model("healthrecordDoctor", HealthrecordDoctorScbema);
