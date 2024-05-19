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
  AppointmentDate: {
    type: String,
    required: true,
  },
  AppointmentTime: {
    type: String,
    required: true,
  },
  DoctorID: {
    type: Number,
    ref: "doctor",
    required: false,
  },
  Speciality: {
    type: String,
    required: true,
  },
  PaymentMethod: {
    type: String,
    required: true,
  },
  Symptom: {
    type: String,
    required: true,
  },
});

AppointmentSchema.plugin(AutoIncrement, {
  id: "appointment_seq",
  inc_field: "_id",
});

export default mongoose.model("appointment", AppointmentSchema);
