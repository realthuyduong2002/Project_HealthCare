import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const PatientSchema = new Schema({
  _id: Number,
  PatientName: {
    type: String,
    required: false,
  },
  Phone: {
    type: String,
    required: false,
  },
  Email: {
    type: String,
    required: false,
  },
  City: {
    type: String,
    required: false,
  },
  District: {
    type: String,
    required: false,
  },
  Ward: {
    type: String,
    required: false,
  },
  Gender: {
    type: String,
    required: false,
  },
  DateOfBirth: {
    type: String,
    required: false,
  },
  CitizenIdentification: {
    type: String,
    required: false,
  },
});

// Apply the plugin to auto-increment the _id field
PatientSchema.plugin(AutoIncrement, { id: "patient_seq", inc_field: "_id" });

const PatientModel = mongoose.model("Patient", PatientSchema);

export default PatientModel;
