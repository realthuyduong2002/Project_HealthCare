import Prescription from "../models/prescription.js";
import Patient from "../models/patient.js";
import mongoose from "mongoose";

//GET ALL PRESCRIPTION
export const getAllPrescription = async (req, res, next) => {
  try {
    const prescriptions = await Prescription.find();
    res.status(200).json(prescriptions);
  } catch (error) {
    console.error("Error in get all prescription: ", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

//GET PRESCRIPTION BY ID
export const getPrescriptionByID = async (req, res, next) => {
  try {
    const prescription = await Prescription.findById(req.params.id);
    if (!prescription) {
      return res.status(404).json({ error: "Prescription not found" });
    }
    res.status(200).json(prescription);
  } catch (error) {
    console.error("Error in get prescription by ID", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};

//ADD PRESCRIPTION
export const addPrescription = async (req, res, next) => {
  const { PatientID, Description, NumberOfMedicine } = req.body;

  const exsitingPatient = await Patient.findById(PatientID);
  if (!exsitingPatient) {
    return res.status(404).json("Patient not found in prescription");
  }

  try {
    const newPrescription = new Prescription({
      PatientID: exsitingPatient._id,
      Description,
      NumberOfMedicine,
    });
    const savedPrescription = await newPrescription.save();
    res.status(200).json(savedPrescription);
  } catch (error) {
    console.error("Error in addPrescription");
    res.status(500).json({ error: "Internal server error" });
  }
};

//UPDATE PRESCRIPTION
export const updatePrescription = async (req, res, next) => {
  try {
    const prescriptionId = req.params.id;
    const updateData = req.body;

    const updatePrescription = await Prescription.findByIdAndUpdate(
      prescriptionId,
      updateData,
      { new: true }
    );
    if (!updatePrescription) {
      return res.status(404).json({ error: "Prescription not found" });
    }
    res.status(200).json(updatePrescription);
  } catch (error) {
    console.error("Error in update Prescription: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

//DELETE PRESCRIPTION
export const deletePrescription = async (req, res, next) => {
  try {
    const prescriptionId = req.params.id;

    const deletePrescription = await Prescription.findOneAndDelete(
      prescriptionId
    );
    if (!deletePrescription) {
      return res.status(404).json("Prescription not found");
    }
    return res.status(200).json({ message: "Delete successful" });
  } catch (error) {
    console.error("Error in deletePrescription: ", error);
    res.status(500).json({ error: "Internal server errror" });
  }
};
