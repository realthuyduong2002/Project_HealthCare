import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const AppointmentSchema = new mongoose.Schema({
  _id: Number,
  PatientID: {
    type: Number,
    ref: "patient",
    required: true,
  },
  PatientName: {
    type: String,
    required: false,
  },
  AppointmentDate: {
    type: String,
    required: true,
  },
  AppointmentTime: {
    type: String,
    required: true,
  },
  DoctorID: {
    type: String,
    ref: "doctor",
    required: false,
  },
  DoctorName: {
    type: String,
    require: true,
  },
  Speciality: {
    type: String,
    required: true,
  },
  Symptom: {
    type: String,
    required: true,
  },
  Status: {
    type: String,
    enum: ["Pending", "Reject", "Accept"], // Specify enum values
    default: "Pending", // Set default value to Pending
  },
});

AppointmentSchema.plugin(AutoIncrement, {
  id: "appointment_seq",
  inc_field: "_id",
});

export default mongoose.model("appointment", AppointmentSchema);
