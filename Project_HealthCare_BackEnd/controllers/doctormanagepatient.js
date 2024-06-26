import express from "express";
import Patient from "../models/patient.js";
import Record from "../models/healthRecord.js";
import Prescription from "../models/prescription.js";
import Bill from "../models/bill.js";
import Appointment from "../models/appointment.js";
import Therapeutic from "../models/therapeutic.js";
import mongoose from "mongoose";

//Get all patient
export const getAllPatient = async (req, res, next) => {
  try {
    const patient = await Patient.find();
    return res.status(200).json(patient);
  } catch (error) {
    return res.status(500).json({ error: "Not exist any patient" });
  }
};

//Get one patient
export const getPatientById = async (req, res, next) => {
  try {
    const patient = await Patient.findById(req.params.id);
    if (!patient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    return res.status(200).json(patient);
  } catch (error) {
    return res.status(500).json({ error: "Not exist any patient" });
  }
};

//Add new patient
export const addPatient = async (req, res) => {
  try {
    const {
      PatientName,
      Phone,
      Email,
      City,
      District,
      Ward,
      Gender,
      DateOfBirth,
      CitizenIdentification,
    } = req.body;

    const patient = new Patient({
      PatientName,
      Phone,
      Email,
      City,
      District,
      Ward,
      Gender,
      DateOfBirth,
      CitizenIdentification,
    });

    await patient.save();

    return res.status(200).json(patient);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal server error" });
  }
};

export const updatePatientById = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;
    const updateData = req.body;

    // Exclude the _id field from the updateData
    delete updateData._id;

    const updatedPatient = await Patient.findByIdAndUpdate(
      patientId,
      updateData,
      { new: true }
    );

    if (!updatedPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }

    return res.status(200).json(updatedPatient);
  } catch (error) {
    console.error("Error in updating patient: ", error);
    return res.status(500).json({ error: "Internal server error" });
  }
};
