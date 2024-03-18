import mongoose from "mongoose";

const { Schema } = mongoose;

const DoctorSchema = new mongoose.Schema({
  PrescriptionID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "prescription",
    },
  ],
  _id: Number,
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
    type: Number,
    require: true,
  },
  Specialty: {
    type: String,
    require: true,
  },
  WorkingDate: {
    type: Date,
    require: true,
  },
  WorkingTime: {
    type: Date,
    require: true,
  },
});

export default mongoose.model("doctor", DoctorSchema);
