import mongoose from "mongoose";

const { Schema } = mongoose;

const PrescriptionShema = new mongoose.Schema({
  Description: {
    type: String,
    require: true,
  },
  NumberOfMedicine: {
    type: Number,
    require: true,
  },
});

export default mongoose.model("prescription", PrescriptionShema);
