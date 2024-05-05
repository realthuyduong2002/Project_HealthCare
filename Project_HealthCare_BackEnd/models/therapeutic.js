import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);
const { Schema } = mongoose;

const TherapeuticSchema = new mongoose.Schema({
  _id: Number,
  PatientID: { type: Number, ref: "patient", require: true },
  Note: {
    type: String,
    required: true,
  },
  ReExamination: {
    type: String,
    required: true,
  },
});

TherapeuticSchema.plugin(AutoIncrement, {
  id: "therapeutic_seq",
  inc_field: "_id",
});

export default mongoose.model("Therapeutic", TherapeuticSchema);
