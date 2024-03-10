import mongoose, { Schema } from "mongoose";

const { Schema } = mongoose;

const AppointmentSchema = new mongoose.Schema({
  BillID: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: "bill",
    },
  ],
  AppointmentDate: {
    type: Date,
    require: true,
  },
  AppointmentTime: {
    type: Date,
    require: true,
  },
  AppointmentType: {
    type: String,
    require: true,
  },
});

export default mongoose.model("appointment", AppointmentSchema);
