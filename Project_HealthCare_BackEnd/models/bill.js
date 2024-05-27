import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";
import appointment from "./appointment.js";

const { Schema } = mongoose;

const AutoIncrement = AutoIncrementFactory(mongoose);

const BillSchema = new mongoose.Schema({
  _id: Number,
  PatientID: { type: Number, ref: "Patient", required: true },
  PatientName: { type: String, ref: "Patient", required: true },
  AppointmentDate: { type: String, ref: "Appointment", required: true },
  AppointmentTime: { type: String, ref: "Appointment", required: true },
  AppointmentID: { type: Number, ref: "Appointment", required: true }, // Corrected field name
  TotalCost: { type: Number, default: "5.86" },
  DateCreate: { type: String, required: true },
});

BillSchema.plugin(AutoIncrement, {
  id: "bill_seq",
  inc_field: "_id",
});

export default mongoose.model("Bill", BillSchema);
