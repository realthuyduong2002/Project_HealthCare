import mongoose from "mongoose";
import AutoIncrementFactory from "mongoose-sequence";

const AutoIncrement = AutoIncrementFactory(mongoose);

const { Schema } = mongoose;

const AccountSchema = new mongoose.Schema({
  _id: Number,
  PhoneNumber: {
    type: String,
    unique: true,
    require: true,
  },
  Password: {
    type: String,
    require: true,
  },
  Role: {
    type: Boolean,
  },
});

AccountSchema.plugin(AutoIncrement, {
  id: "account_seq",
  inc_field: "_id",
});

export default mongoose.model("account", AccountSchema);
