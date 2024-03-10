import mongoose from "mongoose";

const { Schema } = mongoose;

const AccountSchema = new mongoose.model({
  PatientID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "patient",
    },
  ],
  DoctorID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "doctor",
    },
  ],
  PhoneNumber: {
    type: String,
    require: true,
  },
  Password: {
    type: String,
    require: true,
  },
});

export default mongoose.model("account", AccountSchema);
