import express from "express";
import dotenv from "dotenv";
import mongoose from "mongoose";
import doctorTherapeuticRoute from "./routes/doctormanagetherapeutic.js";
import doctorPrescriptionRoute from "./routes/doctormanageprescription.js";
import doctorHealthRecordRoute from "./routes/doctormanagehealthrecord.js";
import patientBillRoute from "./routes/patientmanagebill.js";
import doctorAppointmentRoute from "./routes/doctormanageappointment.js";
import doctorPatientRoute from "./routes/doctormanagepatient.js";
import patientAppointmentRoute from "./routes/patientmanageappointment.js";

const app = express();
dotenv.config();

const connect = async () => {
  try {
    await mongoose.connect(process.env.MONGO);
    console.log("Connected to MongoDB");
  } catch (error) {
    console.error("MongoDB connection error:", error);
    process.exit(1); // Exit the process if MongoDB connection fails
  }
};

// Middleware to parse JSON bodies
app.use(express.json());

// Routes
app.use("/api", doctorTherapeuticRoute);
app.use("/api", doctorPrescriptionRoute);
app.use("/api", doctorHealthRecordRoute);
app.use("/api", patientBillRoute);
app.use("/api", doctorAppointmentRoute);
app.use("/api", doctorPatientRoute);
app.use("/api", patientAppointmentRoute);

app.get("/", (req, res) => {
  res.send("Hello you!");
});

const PORT = process.env.PORT || 8080;

app.listen(PORT, async () => {
  await connect(); // Connect to MongoDB when the server starts
  console.log(`Server is running on port ${PORT}`);
});
