import mongoose from "mongoose";

const { Schema } = mongoose;

const HealthRecordSchema = new mongoose.Schema({
  HeartBeat: {
    type: Number,
    require: true,
  },
  CreationDate: {
    type: Date,
    require: true,
  },
  Height: {
    type: Number,
    require: true,
  },
  Weight: {
    type: Number,
    require: true,
  },

  TypeOfPicture: {
    type: String,
    require: true,
  },
  BloodPressure: {
    type: String,
    require: true,
  },
  UnderlyingDisease: {
    type: String,
    require: true,
  },
});

export default mongoose.model("healthRecord", HealthRecordSchema);
