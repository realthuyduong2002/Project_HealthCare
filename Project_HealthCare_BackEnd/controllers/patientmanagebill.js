import Bill from "../models/bill.js";
import Patient from "../models/patient.js";
import Appointment from "../models/appointment.js";

// Get all bills for a patient
export const getAllBill = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;
    const existingPatient = await Patient.findById(patientId);

    if (!existingPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }

    const bills = await Bill.find({ PatientID: patientId });
    return res.status(200).json(bills);
  } catch (error) {
    console.error("Error in getAllBills: ", error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

// Get a bill by its ID
export const getBillByID = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;

    const existingPatient = await Patient.findOne({ _id: patientId });
    if (!existingPatient) {
      return res.status(404).json("Patient not found");
    }

    const latestBill = await Bill.findOne({
      PatientID: patientId,
    }).sort({ _id: -1 });

    if (!latestBill) {
      return res.status(404).json("No bills found");
    }

    return res.status(200).json(latestBill);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

// Add a new bill
export const addBill = async (req, res) => {
  try {
    const {
      PatientID,
      PatientName,
      AppointmentDate,
      AppointmentTime,
      AppointmentID,
      TotalCost,
      DateCreate,
    } = req.body;

    const existingPatient = await Patient.findById(PatientID);
    const existingAppointment = await Appointment.findById(AppointmentID);

    if (!existingPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    if (!existingAppointment) {
      return res.status(404).json({ error: "Appointment not found" });
    }

    const newBill = new Bill({
      PatientID: existingPatient._id,
      PatientName,
      AppointmentDate,
      AppointmentTime,
      AppointmentID: existingAppointment._id,
      TotalCost,
      DateCreate,
    });

    const savedBill = await newBill.save();

    return res.status(200).json(savedBill);
  } catch (error) {
    console.error("Error in addBill: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
