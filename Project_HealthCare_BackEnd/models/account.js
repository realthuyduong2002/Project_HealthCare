import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const AccountSchema = new mongoose.Schema({
  _id: Number,
  PatientID: {
    type: Number,
    ref: "patient",
    require: true,
  },

  DoctorID: {
    type: Number,
    ref: "doctor",
    require: true,
  },

  PhoneNumber: {
    type: String,
    require: true,
  },
  Password: {
    type: String,
    require: true,
  },
});

AccountSchema.plugin(AutoIncrement, {
  id: "account_seq",
  inc_field: "_id",
});

export default mongoose.model("account", AccountSchema);
