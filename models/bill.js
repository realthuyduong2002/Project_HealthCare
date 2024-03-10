import mongoose from "mongoose";

const { Schema } = mongoose;

const BillSchema = new mongoose.Schema({
  TotalCost: {
    type: Number,
    require: true,
  },
  OrdinalNumber: {
    type: Number,
    require: true,
  },
  DateCreate: {
    type: Date,
    require: true,
  },
});

export default mongoose.model("bill", BillSchema);
