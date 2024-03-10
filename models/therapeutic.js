import mongoose from "mongoose";

const { Schema } = mongoose;

const TherapeuticSchema = new mongoose.Schema({
  Note: {
    type: String,
    require: true,
  },
  ReExamination: {
    type: String,
    require: true,
  },
});

export default mongoose.model("therapeutic", TherapeuticSchema);
