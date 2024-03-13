import mongoose from "mongoose";

const { Schema } = mongoose;

const CounterSchema = new mongoose.Schema({
  _id: String,
  sequence_value: {
    type: Number,
    default: 0,
  },
});

export default mongoose.model("Counter", CounterSchema);
