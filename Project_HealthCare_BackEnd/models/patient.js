import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const PatientSchema = new Schema({
  _id: Number,
  PatientName: {
    type: String,
    required: true,
  },
  Phone: {
    type: String,
    required: true,
  },
  Email: {
    type: String,
    required: true,
  },
  Address: {
    type: String,
    required: true,
  },
  Gender: {
    type: String,
    required: true,
  },
  DateOfBirth: {
    type: String,
    required: true,
  },
  CitizenIdentification: {
    type: String,
    required: true,
  },
});

// Apply the plugin to auto-increment the _id field
PatientSchema.plugin(AutoIncrement, { id: "patient_seq", inc_field: "_id" });

const PatientModel = mongoose.model("Patient", PatientSchema);

export default PatientModel;
