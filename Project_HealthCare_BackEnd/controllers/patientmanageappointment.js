import express from "express";
import Appointment from "../models/appointment.js";
import Bill from "../models/bill.js";
import Patient from "../models/patient.js";
import mongoose from "mongoose";

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
      AppointmentType,
      PaymentMethod,
    } = req.body;

    // Check if the patient and bill exist
    const existingPatient = await Patient.findById(PatientID);

    if (!existingPatient) {
      return res
        .status(404)
        .json({ success: false, error: "Patient not found" });
    }

    // Create new appointment
    const appointment = new Appointment({
      PatientID: existingPatient._id,
      AppointmentDate,
      AppointmentTime,
      AppointmentType,
      PaymentMethod,
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
