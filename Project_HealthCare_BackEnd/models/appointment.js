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
  BillID: {
    type: Number,
    ref: "bill",
    required: true,
  },
  AppointmentDate: {
    type: Date,
    required: true,
  },
  AppointmentTime: {
    type: Date,
    required: true,
  },
  AppointmentType: {
    type: String,
    required: true,
  },
  PaymentMethod: {
    type: String,
    required: true,
  },
});

AppointmentSchema.plugin(AutoIncrement, {
  id: "appointment_seq",
  inc_field: "_id",
});

export default mongoose.model("appointment", AppointmentSchema);
