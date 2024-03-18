import express from "express";
import dotenv from "dotenv";
import mongoose from "mongoose";
import therapeuticRoute from "./routes/managetherapeutic.js";
import doctorRoute from "./routes/managedoctor.js";
import medicineRoute from "./routes/managemedicine.js";

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
app.use("/api", therapeuticRoute);
app.use("/api", doctorRoute);
app.use("/api", medicineRoute);

app.get("/", (req, res) => {
  res.send("Hello you!");
});

const PORT = process.env.PORT || 8080;

app.listen(PORT, async () => {
  await connect(); // Connect to MongoDB when the server starts
  console.log(`Server is running on port ${PORT}`);
});
