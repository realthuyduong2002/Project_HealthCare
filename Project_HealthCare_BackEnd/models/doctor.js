import mongoose from "mongoose";

const { Schema } = mongoose;

const DoctorSchema = new mongoose.Schema({
  accountId: {
    type: Number,
    ref: 'Account',
    required: true
  },
  DoctorName: {
    type: String,
    require: true,
  },
  Gender: {
    type: String,
    require: true,
  },
  Email: {
    type: String,
    require: true,
  },
  Phone: {
    type: String,
    require: true,
  },
  DateOfBirth: {
    type: String,
    required: true,
  },
  City: {
    type: String,
    require: false,
  },
  Speciality: {
    type: String,
    require: true,
  },
  WorkingDate: {
    type: Date,
    require: true,
  },
  WorkingTime: {
    type: String,
    require: true,
  },
});

export default mongoose.model("doctor", DoctorSchema);
