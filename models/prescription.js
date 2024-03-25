import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);
const { Schema } = mongoose;

const PrescriptionShema = new mongoose.Schema({
  _id: Number,
  PatientID: { type: Number, ref: "patient", require: true },
  Description: {
    type: String,
    require: true,
  },
  NumberOfMedicine: {
    type: Number,
    require: true,
  },
});

PrescriptionShema.plugin(AutoIncrement, {
  id: "prescription_seq",
  inc_field: "_id",
});

export default mongoose.model("prescription", PrescriptionShema);
