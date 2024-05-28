import express from "express";
import Appointment from "../models/appointment.js";
import Patient from "../models/patient.js";
import mongoose from "mongoose";
import Doctor from "../models/doctor.js";

export const getAllAppointment = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;
    console.log("Patient ID: ", patientId);
    const appointments = await Appointment.find({ PatientID: patientId });
    console.log("Appointments: ", appointments);
    res.json(appointments);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Internal server error" });
  }
};

//Get appointment by ID according to patientID
export const getAppointmentByID = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;
    const appointmentId = req.params.id;

    const existingPatient = await Patient.findById(patientId);
    if (!existingPatient) {
      return res.status(404).json("Patient not found");
    }

    const appointment = await Appointment.findOne({
      PatientID: patientId,
      _id: appointmentId,
    });

    if (!appointment) {
      return res.status(404).json("Appointment not found");
    }
    return res.status(200).json(appointment);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

//Add new appointment
export const addAppointment = async (req, res) => {
  try {
    const {
      PatientID,
      AppointmentDate,
      AppointmentTime,
      DoctorID,
      Speciality,
      Symptom,
    } = req.body;

    // Check if the patient and bill exist
    const existingPatient = await Patient.findById(PatientID);
    const existingDoctor = await Doctor.findById(DoctorID);

    if (!existingPatient) {
      return res
        .status(404)
        .json({ success: false, error: "Patient not found" });
    }

    if (!existingDoctor) {
      return res
        .status(404)
        .json({ success: false, error: "Doctor not found" });
    }

    // Create new appointment
    const appointment = new Appointment({
      PatientID: existingPatient._id,
      AppointmentDate,
      AppointmentTime,
      DoctorID: existingDoctor._id,
      Speciality,
      Symptom,
    });

    // Save appointment to database
    await appointment.save();

    return res.status(200).json({ success: true, appointment });
  } catch (error) {
    console.error(error);
    return res
      .status(500)
      .json({ success: false, error: "Internal server error" });
  }
};

export const getLatestAppointment = async (req, res, next) => {
  try {
    const patientId = req.params.PatientID;

    const existingPatient = await Patient.findOne({ _id: patientId });
    if (!existingPatient) {
      return res.status(404).json("Patient not found");
    }

    const latestAppointment = await Appointment.findOne({
      PatientID: patientId,
    }).sort({ _id: -1 });

    if (!latestAppointment) {
      return res.status(404).json("No appointments found");
    }

    return res.status(200).json(latestAppointment);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
};

export const cancelAppointment = async (req, res) => {
  try {
    const PatientID = req.params.PatientID; // Assume you pass the patient ID in the URL as /patients/:patientId
    const AppointmentID = req.params.AppointmentID; // Assume you pass the appointment ID in the URL as /appointments/:appointmentId

    // Validate that both IDs are present
    if (!PatientID || !AppointmentID) {
      return res
        .status(400)
        .json({ error: "Patient ID and Appointment ID are required" });
    }

    const existingPatient = await Patient.findOne({ _id: PatientID });
    if (!existingPatient) {
      return res.status(404).json({ error: "Patient not found" });
    }
    const deleteAppointment = await Appointment.findByIdAndDelete({
      _id: AppointmentID,
    });
    if (!deleteAppointment) {
      return res.status(404).json({ error: "Appointment not found" });
    }
    return res.status(200).json("Delete successfully");
  } catch (error) {
    console.error("Error in cancelAppointment:", error);
    res.status(500).json({ error: "Internal Server Error" });
  }
};
