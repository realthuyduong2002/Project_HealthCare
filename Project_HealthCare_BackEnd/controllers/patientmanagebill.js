import Bill from "../models/bill.js";
import Patient from "../models/patient.js";
import Appoinment from "../models/appointment.js";

//Get all bill
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
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

//Get bill by ID
export const getBillByID = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;
    const billId = req.params.id; // Correct variable name to billId

    const existingPatient = await Patient.findById(patientId);
    if (!existingPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }

    // Find the bill by PatientID and _id (BillID)
    const bill = await Bill.findOne({ PatientID: patientId, _id: billId });
    if (!bill) {
      return res.status(404).json({ error: "Bill not found for this patient" });
    }

    return res.status(200).json(bill);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

//Add new bill
export const addBill = async (req, res) => {
  try {
    const { PatientID, AppointmentID, TotalCost, DateCreate } = req.body;

    const existingPatient = await Patient.findById(PatientID);
    const existingAppointment = await Appoinment.findById(PrescriptionID);

    if (!existingPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    if (!existingAppointment) {
      return res.status(404).json({ error: "Appointment not found" });
    }

    const newBill = new Bill({
      PatientID: existingPatient._id,
      TotalCost,
      OrdinalNumber: 0,
      DateCreate,
    });

    const savedBill = await newBill.save();

    return res.status(200).json(savedBill);
  } catch (error) {
    console.error("Error in addBill: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
