import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const HealthRecordSchema = new mongoose.Schema({
  _id: Number,
  PatientID: { type: Number, ref: "patient", require: true },
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

HealthRecordSchema.plugin(AutoIncrement, {
  id: "healthrecord_seq",
  inc_field: "_id",
});

const HealthRecordModel = mongoose.model("HealthRecord", HealthRecordSchema);

export default HealthRecordModel;
