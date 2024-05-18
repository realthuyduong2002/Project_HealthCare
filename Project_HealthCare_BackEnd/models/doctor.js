import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);
const { Schema } = mongoose;

const DoctorSchema = new mongoose.Schema({
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
    type: String,
    require: true,
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

DoctorSchema.plugin(AutoIncrement, {
  id: "doctor_seq",
  inc_field: "_id",
});

export default mongoose.model("doctor", DoctorSchema);
