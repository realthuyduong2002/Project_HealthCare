import mongoose, { Schema } from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const MedicineSchema = new mongoose.Schema({
  _id: Number,
  MedicineCost: {
    type: Number,
    require: true,
  },
  MedicineName: {
    type: String,
    require: true,
  },
  LocationProcedure: {
    type: String,
    require: true,
  },
  MedicineType: {
    type: String,
    require: true,
  },
});

MedicineSchema.plugin(AutoIncrement, {
  id: "medicine_seq",
  inc_field: "_id",
});

export default mongoose.model("medicine", MedicineSchema);
