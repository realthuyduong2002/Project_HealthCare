import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const { Schema } = mongoose;

const AutoIncrement = AutoIncrementFactory(mongoose);

const BillSchema = new mongoose.Schema({
  _id: Number,
  PatientID: { type: Number, ref: "Patient", required: true },
  TotalCost: { type: Number, required: true },
  OrdinalNumber: { type: Number, required: true },
  DateCreate: { type: Date, required: true },
});

BillSchema.plugin(AutoIncrement, {
  id: "bill_seq",
  inc_field: "_id",
});

export default mongoose.model("Bill", BillSchema);
