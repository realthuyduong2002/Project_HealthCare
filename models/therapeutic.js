import mongoose from "mongoose";

const { Schema } = mongoose;

const TherapeuticSchema = new mongoose.Schema({
  _id: Number,
  Note: {
    type: String,
    required: true,
  },
  ReExamination: {
    type: String,
    required: true,
  },
});

export default mongoose.model("Therapeutic", TherapeuticSchema);
