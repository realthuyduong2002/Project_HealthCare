import express from "express";
import dotenv from "dotenv";
import mongoose from "mongoose";
import patientRoute from "./routes/managepatient.js";

const app = express();
dotenv.config();

const connect = async () => {
  try {
    await mongoose.connect(process.env.MONGO);
    console.log("Connected to MongoDB");
  } catch (error) {
    throw error;
  }
};

mongoose.connection.on("connected", () => {
  console.log("Mongoose connected!");
});

mongoose.connection.on("disconnected", () => {
  console.log("mongoDB disconnected!");
});

app.use("/api", patientRoute);

app.get("/", (req, res) => {
  res.send("Hello you!");
});

app.listen(8080, () => {
  connect();
  console.log("Connected to backend.");
});
