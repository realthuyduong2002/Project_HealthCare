import mongoose from "mongoose";

const { Schema } = mongoose;

const PatientSchema = new mongoose.Schema({
  RecordID: [{ type: mongoose.Schema.Types.ObjectId, ref: "record" }],
  PrescriptionID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "prescription",
    },
  ],
  BillId: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "bill",
    },
  ],
  AppointmentID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "appointment",
    },
  ],
  TherapeuticID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "therapeutic",
    },
  ],
  PatientName: {
    type: String,
    required: true,
  },
  Phone: {
    type: String,
    require: true,
  },
  Email: {
    type: String,
    require: true,
  },
  Address: {
    type: String,
    require: true,
  },
  Gender: {
    type: String,
    require: true,
  },
  DateOfBirth: {
    type: String,
    require: true,
  },
  CitizenIdentification: {
    type: String,
    require: true,
  },
});

export default mongoose.model("patient", PatientSchema);
